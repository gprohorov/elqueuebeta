package com.med.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.Doctor;
import com.med.model.Procedure;
import com.med.model.SalaryDTO;
import com.med.model.Talon;
import com.med.model.statistics.dto.doctor.DoctorPeriodSalary;
import com.med.repository.SalaryDTORepository;

@Service
public class SalaryDTOService {

    @Autowired
    SalaryDTORepository repository;

    @Autowired
    SalaryService salaryService;

    @Autowired
    TalonService talonService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    ProcedureService procedureService;
    
    @Autowired
    SettingsService settingsService;

    private List<Integer> fullTimeList;

    @PostConstruct
    void init() {
    	fullTimeList = doctorService.getAllActive().stream()
            .filter(doc -> doc.getProcedureIds().isEmpty())
            .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        fullTimeList.add(2); // для Иры.
    }

    public List<SalaryDTO> getAll() {
        return repository.findAll();
    }

    public SalaryDTO createSalaryDTO(SalaryDTO salaryDTO) {
        return repository.save(salaryDTO);
    }

    public SalaryDTO updateSalaryDTO(SalaryDTO salaryDTO) {
        return repository.save(this.recalculateDTO(salaryDTO));
    }

    public SalaryDTO getSalaryDTOById(String salaryDTOId) {
        return repository.findById(salaryDTOId).orElse(null);
    }

    public SalaryDTO getSalaryDTOByWeek(int week, int doctorId) {
        return repository.findAll().stream()
                .filter(el->el.getWeek()==week)
                .filter(el->el.getDoctorId()==doctorId)
                .findFirst().orElse(new SalaryDTO());
    }
    
    // главврач генерит зарплату врачу за неделю.
    // Если его что-то не устраивает, то он ПОТОМ
    // он начисляет премию\штраф,  меняет ставку и процент за процедуры ,
    // а потом снова перегенерит.
    // Если всё ОК,  запоминает в базу.
    // Генерить имеет смысл ТОЛЬКО  в субботу после 15.00, когда рабочий день
    // закончен. В любой доугой день - некорректно.
    // В течении недели можно вынуть запись из базы и снова перегенерить.
    // Через неделю в субботу в 18.00 ведомость закрывается и более никаких
    // правок вносить нельзя.
    // Учитывается недобор/перебор из прошлой ведомости.
    // В течение недели ведомость считается валидной и по ней можно получать
    // зарплату. В конце недели ведомость закрывается, а все остатки переносятся
    // в следующую ведомость.

    public SalaryDTO generateRowOfDoctor(int doctorId) {
        SalaryDTO dto = new SalaryDTO();
        LocalDate from = LocalDate.now().minusDays(5);
        LocalDate to = LocalDate.now();

        dto.setFrom(from);
        // System.out.println(from);
        dto.setTo(to);
        // System.out.println(to);
        int week = repository.findAll().stream()
            .filter(el->el.getClosed()==null).findAny()
            .get().getWeek();
        dto.setWeek( week + 1 );
        dto.setDoctorId(doctorId);

        Doctor doctor = doctorService.getDoctor(doctorId);
        dto.setName(doctor.getFullName());
        dto.setKredit(doctor.getKredit());
        int rest = this.generateRestForDoctorFromLastTable(doctor);
        dto.setRest(rest);

        List<Talon> talons = talonService.getAllTallonsBetween(from.minusDays(1),to.plusDays(1))
           .stream()
           // .filter( talon -> talon.getActivity().equals(Activity.EXECUTED) )
           .filter( talon -> talon.getDoctor()!= null )
           .filter( talon -> talon.getDoctor().getId()==doctorId )
           .filter( talon -> talon.getExecutionTime()!= null )
           .collect(Collectors.toList());
        List<LocalDate> daysList = talons.stream()
            .map(talon -> talon.getDate()).distinct().collect(Collectors.toList());

        // int daysWork = (int) talons.stream().map(talon -> talon.getDate()).distinct().count();
        int daysWork = daysList.size();
        // System.out.println(daysWork);
        int daysWithoutSaturdays = (int) daysList.stream()
            .filter(date->!date.getDayOfWeek().equals(DayOfWeek.SATURDAY)).count();
        // System.out.println(daysWithoutSaturdays);
        int daysTax = (int) ChronoUnit.DAYS.between(from.minusDays(1), to.plusDays(1));
        // System.out.println(daysTax);
        dto.setDays(daysWork);

        final int[] hours = {0};
        talons.stream().collect(Collectors.groupingBy(Talon::getDate)).entrySet()
            .forEach(entry -> {
                LocalDateTime begin = entry.getValue().stream()
            		.min(Comparator.comparing(Talon::getStart)).get().getStart();
                LocalDateTime end = entry.getValue().stream()
            		.max(Comparator.comparing(Talon::getExecutionTime)).get().getExecutionTime();
                int hrs = (int) ChronoUnit.HOURS.between(begin, end);
                hours[0] += hrs;
            });
        dto.setHours(hours[0]);

        // TODO: hardcode
        if (fullTimeList.contains(doctorId)) {
        	dto.setDays(6);
        	dto.setHours(40);
        }

        // int stavka = this.generateStavkaForDoctor(doctor, dto.getDays(), dto.getHours());
        int stavka = dto.getHours() * doctor.getRate()
                - daysTax * settingsService.get().getTax()/30
                - daysWithoutSaturdays * settingsService.get().getCanteen();
        dto.setStavka(stavka);
        if (fullTimeList.contains(doctorId)) stavka = 0;
        dto.setStavka(stavka);

        int accural = this.generateBonusesForDoctor(talons);
        dto.setAccural(accural);

        int total = rest + stavka + accural + dto.getAward() - dto.getPenalty();
        dto.setTotal(total);

        dto.setActual(total - dto.getRecd());

        return dto;
    }

    private int generateStavkaForDoctor(Doctor doctor, int days, int hours) {
        return doctor.getRate() * hours - settingsService.get().getTax() * 2 / 9
            - settingsService.get().getCanteen() * ((days == 0) ? 0 : days - 1);
    }

    private int generateBonusesForDoctor(List<Talon> talons) {
        if (talons.isEmpty()) return 0;
        double sum = 0;

        // TODO:  must be refactored.  to doctor = talon.getDoctor
        // impossible without streams !!!
        Doctor doctor = doctorService.getDoctor(talons.get(0).getDoctor().getId());
        for (Talon talon:talons) {
            // TODO:  must be refactored.  to procedure = talon.getProcedure.
            Procedure procedure = procedureService.getProcedure(talon.getProcedureId());
            int zones = talon.getZones();
            int price = procedure.getSOCIAL();
            int percent = doctor.getPercents().stream()
                .filter(item-> item.getProcedureId() == procedure.getId())
                .findAny().get().getProcent();
            double accural = zones * price * percent / 100;
            sum += accural;
        }
        return (int) sum ;
    }

    public SalaryDTO recalculateDTO(SalaryDTO dto) {
        dto.setTotal(dto.getRest() + dto.getStavka() + dto.getAward()
            + dto.getAccural() - dto.getPenalty());
        dto.setActual(dto.getTotal() - dto.getRecd());
        return dto;
    }

    public List<SalaryDTO> generateSalaryWeekTable(LocalDate start, LocalDate finish) {
        List<SalaryDTO> table = new ArrayList<>();
        doctorService.getAllActive().stream().forEach(doctor -> {
            table.add(this.generateRowOfDoctor(doctor.getId()));
        });
        return table;
    }

    // TODO: Make by MongoRepository
    private int generateRestForDoctorFromLastTable(Doctor doctor) {
        SalaryDTO dto = repository.findAll().stream()
            .filter(el -> el.getClosed() == null)
            .filter(el -> el.getDoctorId() == doctor.getId())
            .findAny().orElse(null);
        return (dto != null) ? dto.getActual() : 0;
    }

    // TODO: Make by MongoRepository
    public List<SalaryDTO> getOpenTable() {
        return repository.findAll().stream().filter(dto->dto.getClosed() == null)
            .collect(Collectors.toList());
    }

    // Генериться автоматически КРОНОМ
    // в субботу, когда все свалят,
    // генерится зарплатная ведомость за прошедшую неделю (пн-сб)
    // ПРИ ЭТОМ актуальная ведомость (за позпрошлую неделю закрывается,
    // а остатки из неё переносятся в новую таблу)
    // Новая ведомость заносится в базу и становится актуальной
    //  хоз двору начисляются только дни и часы,  зп им в конце мксяца
    //  Ире -регистратура ()  ставка тоже в конце месяца, а бонусы   начисляются здесь

    @Scheduled(cron = "0 0 16 ? * SAT")
    public List<SalaryDTO> createNewTable() {
        LocalDate today = LocalDate.now();
        List<SalaryDTO> list = this.generateSalaryWeekTable(today.minusDays(6), today.plusDays(1));
        // TODO: Make by MongoRepository
        List<SalaryDTO> expiredList = repository.findAll().stream()
            .filter(row->row.getClosed() == null).collect(Collectors.toList());
        expiredList.stream().forEach(row -> row.setClosed(LocalDateTime.now()));
        repository.saveAll(expiredList);
        return repository.saveAll(list);
    }

    // TODO: Make by MongoRepository
    // получить ведомость по номеру недели
    public List<SalaryDTO> getTableByWeek(int week) {
        return this.getAll().stream().filter(dto->dto.getWeek() == week)
            .sorted(Comparator.comparing(SalaryDTO::getDoctorId))
            .collect(Collectors.toList());
    }

    // TODO: Make by MongoRepository
    public SalaryDTO payDoctorSalary(int doctorId, int suma) {
        SalaryDTO dto = repository.findAll().stream()
            .filter(el -> el.getClosed() == null)
            .filter(el -> el.getDoctorId() == doctorId)
            .findAny().orElse(null);
        dto.setRecd(dto.getRecd() + suma);
        return this.updateSalaryDTO(dto);
    }

    public SalaryDTO recalculateDTO(int doctorId) {
    	Doctor doctor = doctorService.getDoctor(doctorId);
    	// TODO: Make by MongoRepository
        SalaryDTO dto = repository.findAll().stream()
            .filter(el->el.getClosed() == null)
            .filter(el->el.getDoctorId() == doctorId)
            .findAny().orElse(null);

        if (dto == null) return null;

        int stavka = this.generateStavkaForDoctor(doctor, dto.getDays(), dto.getHours());
        if (fullTimeList.contains(doctorId)) {
            if (dto.getStavka() == 0) stavka = 0;
            else stavka = doctor.getRate();
        }
        dto.setStavka(stavka);
        dto.setKredit(doctor.getKredit());

        LocalDate from = dto.getFrom();
        LocalDate to = dto.getTo();

        List<Talon> talons = talonService.getAllTallonsBetween(from.minusDays(1), to.plusDays(1))
            .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .filter(talon -> talon.getDoctor().getId() == doctorId)
            .collect(Collectors.toList());

        int accural = this.generateBonusesForDoctor(talons);
        dto.setAccural(accural);

        return this.updateSalaryDTO(dto);
    }

    // суммируем данные по зарплате по врачу за период,
    // например, за месяц. А то у нас по неделям.
    // Период пока не указан. По умолч. с 29.10 по настоящее время
    public SalaryDTO getDoctorSummarySalary(int doctorId) {
        SalaryDTO dto = new SalaryDTO();
        dto.setDoctorId(doctorId);
        dto.setName(doctorService.getDoctor(doctorId).getFullName());

        // TODO: Remove this hardcoded shit
        int startWeek = 43;
        int endWeek = this.getAll().stream().mapToInt(SalaryDTO::getWeek).max().getAsInt();

        // TODO: Make by MongoRepository
        LocalDate startDate = this.getAll().stream()
            .filter(el -> el.getWeek() == startWeek).findAny().get().getFrom();
        dto.setFrom(startDate);

        // TODO: Make by MongoRepository
        LocalDate endDate = this.getAll().stream()
            .filter(el -> el.getWeek() == endWeek).findAny().get().getTo();
        dto.setTo(endDate);

        // TODO: Make by MongoRepository
        List<SalaryDTO> list = this.getAll().stream()
            .filter(el -> el.getDoctorId() == doctorId)
            .filter(el -> (el.getWeek() >= startWeek && el.getWeek() <= endWeek))
            .collect(Collectors.toList());

        int hours = list.stream().mapToInt(SalaryDTO::getHours).sum();
        dto.setHours(hours);

        int days = list.stream().mapToInt(SalaryDTO::getDays).sum();
        dto.setDays(days);

        int stavka = list.stream().mapToInt(SalaryDTO::getStavka).sum();
        dto.setStavka(stavka);

        int accural = list.stream().mapToInt(SalaryDTO::getAccural).sum();
        dto.setAccural(accural);

        int award = list.stream().mapToInt(SalaryDTO::getAward).sum();
        dto.setAward(award);

        int penalty = list.stream().mapToInt(SalaryDTO::getPenalty).sum();
        dto.setPenalty(penalty);

        int kredit = doctorService.getDoctor(doctorId).getKredit();
        dto.setKredit(kredit);

        int recd = list.stream().mapToInt(SalaryDTO::getRecd).sum();
        dto.setRecd(recd);

        int total = stavka + accural + award  - penalty;
        dto.setTotal(total);

        int actual = total - recd;
        dto.setActual(actual);

        return dto;
    }

    // итог по всем врачам за период
    public List<SalaryDTO> getSummarySalaryList(LocalDate from, LocalDate to) {
        List<SalaryDTO> list = new ArrayList<>();
        doctorService.getAllActive().stream().forEach(doctor -> {
             list.add(this.getDoctorSummarySalary(doctor.getId(), from, to));
        });
        return list;
    }

    // каждый месяц 28 числа в 16.10  начисляем зп хоздвору
    @Scheduled(cron = "0 10 16 28 * ?")
    public List<SalaryDTO> injectSalaryForKhozDvor() {
        List<SalaryDTO> list = this.getOpenTable().stream()
            .filter(item-> fullTimeList.contains(item.getDoctorId()))
            .collect(Collectors.toList());
        list.stream().forEach(item -> {
            item.setStavka(doctorService.getDoctor(item.getDoctorId()).getRate());
            item = this.recalculateDTO(item);
        });
        return repository.saveAll(list);
    }
    
    //  полный отчет по доктору за указанный период
    // не совсем корректно, ведь у нас дискретность - неделя
    public SalaryDTO getDoctorSummarySalary(int doctorId, LocalDate from, LocalDate to) {
        SalaryDTO dto = new SalaryDTO();
        dto.setDoctorId(doctorId);
        dto.setName(doctorService.getDoctor(doctorId).getFullName());

        // int startWeek = from.getDayOfYear()/7;
        // int endWeek = to.getDayOfYear()/7;
        //  if (endWeek <43 ) endWeek = endWeek + 52;

        dto.setFrom(from);
        dto.setTo(to);
        // System.out.println(from);
        // System.out.println(to);
        // if (from.isAfter(LocalDate.now().minusDays(7))) from = LocalDate.now();

        // TODO: Make by MongoRepository

        List<SalaryDTO> list = this.getAll().stream()
            .filter(el -> el.getDoctorId() == doctorId)
            //.filter(el -> (el.getWeek() >= startWeek && el.getWeek() <= endWeek))
            .filter(el -> el.getFrom().isAfter(from.minusDays(1)))
            .filter(el -> el.getTo().isBefore(to.plusDays(1)))
            .collect(Collectors.toList());

        int hours = list.stream().mapToInt(SalaryDTO::getHours).sum();
        dto.setHours(hours);

        int days = list.stream().mapToInt(SalaryDTO::getDays).sum();
        dto.setDays(days);

        int stavka = list.stream().mapToInt(SalaryDTO::getStavka).sum();
        dto.setStavka(stavka);

        int accural = list.stream().mapToInt(SalaryDTO::getAccural).sum();
        dto.setAccural(accural);

        int award = list.stream().mapToInt(SalaryDTO::getAward).sum();
        dto.setAward(award);

        int penalty = list.stream().mapToInt(SalaryDTO::getPenalty).sum();
        dto.setPenalty(penalty);

        int kredit = doctorService.getDoctor(doctorId).getKredit();
        dto.setKredit(kredit);

        int recd = list.stream().mapToInt(SalaryDTO::getRecd).sum();
        dto.setRecd(recd);

        int rest = list.stream().sorted(Comparator.comparing(SalaryDTO::getFrom)).findFirst()
            .orElse(new SalaryDTO()).getRest();
        dto.setRest(rest);

        int total = stavka + accural + award + rest - penalty;
        dto.setTotal(total);

        int actual = total - recd;
        dto.setActual(actual);

        return  dto;
    }

    public DoctorPeriodSalary getDoctorSalaryForPeriod(int doctorId, LocalDate from, LocalDate to) {
        DoctorPeriodSalary dto = new DoctorPeriodSalary(doctorId, from, to);
        Doctor doctor = doctorService.getDoctor(doctorId);
        dto.setName(doctor.getFullName());
        List<Talon> talons = talonService.getAllTallonsBetween(from, to)
            .stream()
            .filter( talon -> talon.getDoctor() != null )
            .filter( talon -> talon.getDoctor().getId() == doctorId )
            .filter( talon -> talon.getExecutionTime() != null )
            .collect(Collectors.toList());

        List<LocalDate> dateList = talons.stream().map(talon -> talon.getDate()).collect(Collectors.toList());

        int days = (int) dateList.stream().distinct().count();
        dto.setDays(days);

        final int[] hours = {0};
        talons.stream().collect(Collectors.groupingBy(Talon::getDate)).entrySet()
            .forEach(entry -> {
                LocalDateTime begin = entry.getValue().stream()
                    .min(Comparator.comparing(Talon::getStart)).get().getStart();
                LocalDateTime end = entry.getValue().stream()
                    .max(Comparator.comparing(Talon::getExecutionTime)).get().getExecutionTime();
                int hrs = (int) ChronoUnit.HOURS.between(begin, end);
                hours[0] += hrs;
            });
        dto.setHours(hours[0]);
        if (doctorId == 1) dto.setHours(days * 8);

        int daysWithoutSaturdays = (int) dateList.stream().distinct()
            .filter(date->!date.getDayOfWeek().equals(DayOfWeek.SATURDAY)).count();
        int daysTax = (int) ChronoUnit.DAYS.between(from.minusDays(1), to.plusDays(1));
        // int daysTax = days;
        int stavka = dto.getHours() * doctor.getRate()
            - daysTax * settingsService.get().getTax() / 30
            - daysWithoutSaturdays * settingsService.get().getCanteen();
        if (doctorId == 2) {
            dto.setDays(daysTax);
            dto.setHours(daysTax*8);
            stavka = (doctor.getRate() / 30) * daysTax;
        }
        dto.setStavka(stavka);

        double bonuses = 0;

        for (Talon talon:talons) {
            Procedure procedure = procedureService.getProcedure(talon.getProcedureId());
            int zones = talon.getZones();
            int price = procedure.getSOCIAL();
            int percent = doctor.getPercents().stream()
                .filter(item-> item.getProcedureId() == procedure.getId())
                .findAny().get().getProcent();
            double accural = zones * price * percent / 100;
            bonuses += accural;
        }
        dto.setAccural((int) bonuses);
        dto.setTotal( dto.getStavka() + dto.getAccural() );
        return dto;
    }

    // инжекция разных кверей. Так, на всякий случай.
    public List<SalaryDTO> inject() {
    	/*
        List<SalaryDTO> list = repository.findAll().stream()
            .filter(dto->dto.getWeek()==52)
            .collect(Collectors.toList());
        repository.deleteAll(list);
        repository.findAll().stream()
                .filter(dto->dto.getWeek()==48)
                .filter(dto->!fullTimeList.contains(dto.getDoctorId()))
                .forEach(dto->{
                    dto.setStavka(dto.getStavka()-450);
                    dto.setClosed(null);
                    this.updateSalaryDTO(dto);
                });
        repository.findAll().stream()
                .filter(dto->dto.getWeek()==1)
                .forEach(dto->{
                    dto.setWeek(53);
                    repository.save(dto);
                });

    	*/
        return null;
    }
}