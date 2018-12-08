package com.med.services.salarydto.impls;

import com.med.model.*;
import com.med.repository.SalaryDTORepository;
import com.med.repository.SalaryRepository;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import com.med.services.salarydto.interfaces.ISalaryDTOService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 07.11.18.
 */
@Service
public class SalaryDTOServiceImpl implements ISalaryDTOService {

    @Autowired
    SalaryDTORepository repository;

    @Autowired
    SalaryServiceImpl salaryService;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    ProcedureServiceImpl procedureService;

    private final int TAX = 400;
    private final int CANTEEN = 20;


    public List<SalaryDTO> getAll(){
        return repository.findAll();
    }

    @Override
    public SalaryDTO createSalaryDTO(SalaryDTO salaryDTO) {
        return repository.save(salaryDTO);
    }

    public SalaryDTO updateSalaryDTO(SalaryDTO salaryDTO) {
       salaryDTO = this.recalculateDTO(salaryDTO);
        return repository.save(salaryDTO);
    }

    @Override
    public SalaryDTO getSalaryDTOById(String salaryDTOId) {
        return repository.findById(salaryDTOId).orElse(null);
    }

    @Override
    public SalaryDTO getSalaryDTOByWeek(int week, int doctorId) {

        //TODO

        return null;
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

    public SalaryDTO  generateRowOfDoctor(int doctorId){

        SalaryDTO dto = new SalaryDTO();
        LocalDate from = LocalDate.now().minusDays(5);
        LocalDate to = LocalDate.now();

        dto.setFrom(from);
        dto.setTo(to);

        dto.setWeek(from.getDayOfYear()/7 );
        dto.setDoctorId(doctorId);


        Doctor doctor = doctorService.getDoctor(doctorId);
        dto.setName(doctor.getFullName());
        dto.setKredit(doctor.getKredit());

      //  int rest = this.getRestOfDoctorFromTheLastTable(doctorId);

        int rest = this.generateRestForDoctorFromLastTable(doctor);
        dto.setRest(rest);

        List<Talon> talons = talonService.getAllTallonsBetween(from.minusDays(1),to.plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());
        int days = (int) talons.stream().map(talon -> talon.getDate()).distinct().count();
        dto.setDays(days);

        final int[] hours = {0};
        talons.stream().collect(Collectors.groupingBy(Talon::getDate)).entrySet()
                .forEach((entry)->{
                    LocalDateTime begin = entry.getValue().stream().min(Comparator.comparing(Talon::getStart)).get().getStart();
                    LocalDateTime end = entry.getValue().stream().max(Comparator.comparing(Talon::getExecutionTime)).get().getExecutionTime();
                    int hrs = (int) ChronoUnit.HOURS.between(begin,end);
                    hours[0] +=hrs;
                });
        dto.setHours(hours[0]);
        if (doctorId==2 || doctorId==1){dto.setHours(days*8);}

        //TODO:  hardcode
        if (doctorId>16)
        {   dto.setDays(6);
            dto.setHours(40);}




        int stavka = this.generateStavkaForDoctor(doctor,dto.getDays(),dto.getHours());
        dto.setStavka(stavka);

        int accural = this.generateBonusesForDoctor(talons);
        dto.setAccural(accural);

        int total = rest + stavka + accural + dto.getAward() - dto.getPenalty();
        dto.setTotal(total);

        dto.setActual(total - dto.getRecd());

        return dto;
    }
/*

    private int getRestOfDoctorFromTheLastTable(int doctorId){
      return repository.findAll().stream().filter(dto->dto.getDoctorId()==doctorId)
             .filter(dto->dto.getClosed()==null).findFirst().orElse(new SalaryDTO())
             .getActual();
    }
*/

    private int generateStavkaForDoctor(Doctor doctor,int days, int hours){
        return doctor.getRate()*hours - TAX - days*CANTEEN;
    }

    private int generateBonusesForDoctor(List<Talon> talons){
        if (talons.isEmpty()) return 0;
        double sum = 0;

        //TODO:  must be refactored.  to doctor = talon.getDoctor
        Doctor doctor = doctorService.getDoctor(talons.get(0).getDoctor().getId());
        for (Talon talon:talons){
            //TODO:  must be refactored.  to procedure = talon.getProcedure.
            Procedure procedure = procedureService.getProcedure(talon.getProcedureId());
            int zones = talon.getZones();
            int price = procedure.getSOCIAL();
            int percent = doctor.getPercents().stream()
                    .filter(item-> item.getProcedureId()==procedure.getId())
                    .findAny().get().getProcent();
            double accural = zones * price * percent/100;
            sum+=accural;
        }

        return (int) sum ;
    }

    public SalaryDTO recalculateDTO(SalaryDTO dto){
        dto.setTotal(dto.getRest() + dto.getStavka() + dto.getAward()
                        + dto.getAccural() - dto.getPenalty());
        dto.setActual(dto.getTotal() - dto.getRecd());
        return dto;
    }

    public List<SalaryDTO> generateSalaryWeekTable(LocalDate start, LocalDate finish) {
        List<SalaryDTO> table = new ArrayList<>();
        //finish = LocalDate.now();
       // start = finish.minusDays(6);

        doctorService.getAll().stream().forEach(doctor -> {

            table.add(this.generateRowOfDoctor(doctor.getId()));

        });

        return table;
    }

    private int generateRestForDoctorFromLastTable(Doctor doctor){
        SalaryDTO dto = repository.findAll().stream()
                .filter(el->el.getClosed()==null)
                .filter(el->el.getDoctorId()==doctor.getId())
                .findAny().orElse(null);
       int rest =0;
         if(dto!=null) {
             rest = dto.getActual();
         //    dto.setClosed(LocalDateTime.now());
           //   repository.save(dto);
         }
        return rest;
    }

    public List<SalaryDTO> getOpenTable(){
        return repository.findAll().stream().filter(dto->dto.getClosed()==null)
                .collect(Collectors.toList());
    }

    // Генериться автоматически КРОНОМ
    // в субботу, когда все свалят,
    // генерится зарплатная ведомость за прошедшую неделю (пн-сб)
    // ПРИ ЭТОМ
    //  актуальная ведомость (за позпрошлую неделю закрывается,
    // а остатки из неё переносятся в новую таблу) Hope1234
    // Новая ведомость заносится в базу и становится актуальной

    @Scheduled(cron = "0 30 16 ? * SAT")
    public List<SalaryDTO> createNewTable(){
        LocalDate today = LocalDate.now();
        List<SalaryDTO> list =
                this.generateSalaryWeekTable(today.minusDays(6), today.plusDays(1));
        List<SalaryDTO> expiredList =repository.findAll().stream()
                .filter(row->row.getClosed()==null)
                .collect(Collectors.toList());
         expiredList.stream().forEach(row->row.setClosed(LocalDateTime.now()));
         repository.saveAll(expiredList);
        System.out.println(" SALARY WEEK TABLE HAS BEEN GENERATED");
        return repository.saveAll(list);
    }

    // получить ведомость по номеру недели
    public List<SalaryDTO> getTableByWeek(int week){
        return this.getAll().stream().filter(dto->dto.getWeek()==week)
                .sorted(Comparator.comparing(SalaryDTO::getDoctorId))
                .collect(Collectors.toList());
    }

   public SalaryDTO payDoctorSalary(int doctorId, int suma){
        SalaryDTO dto = repository.findAll().stream()
                .filter(el->el.getClosed()==null)
                .filter(el->el.getDoctorId()==doctorId)
                .findAny().orElse(null);
        dto.setRecd(dto.getRecd()+ suma);

        return this.updateSalaryDTO(dto);
   }

    public SalaryDTO recalculateDTO(int doctorId){
       Doctor doctor = doctorService.getDoctor(doctorId);

        SalaryDTO dto = repository.findAll().stream()
                .filter(el->el.getClosed()==null)
                .filter(el->el.getDoctorId()==doctorId)
                .findAny().orElse(null);

        if (dto==null) return null;

        int stavka = this.generateStavkaForDoctor(doctor,dto.getDays(),dto.getHours());
        dto.setStavka(stavka);
        dto.setKredit(doctor.getKredit());

        LocalDate from = dto.getFrom();
        LocalDate to = dto.getTo();
        List<Talon> talons = talonService.getAllTallonsBetween(from.minusDays(1),to.plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());

        int accural = this.generateBonusesForDoctor(talons);
        dto.setAccural(accural);

       return this.updateSalaryDTO(dto);
    }



    // суммируем данные по зарплате по врачу за период,
    // например, за месяц. А то у нас по неделям.
    // Период пока не указан. По умолч. с 29.10 по настоящее время
    //
    public SalaryDTO getDoctorSummarySalary(int doctorId){
        SalaryDTO dto = new SalaryDTO();

        dto.setDoctorId(doctorId);
        dto.setName(doctorService.getDoctor(doctorId).getFullName());

        int startWeek = 43;
        int endWeek = this.getAll().stream().mapToInt(SalaryDTO::getWeek)
                .max().getAsInt();

        LocalDate startDate = this.getAll().stream()
                .filter(el-> el.getWeek()==startWeek).findAny()
                .get().getFrom();
        dto.setFrom(startDate);

        LocalDate endDate = this.getAll().stream()
                .filter(el-> el.getWeek()==endWeek).findAny()
                .get().getTo();
        dto.setTo(endDate);

        List<SalaryDTO> list = this.getAll().stream()
                .filter(el->el.getDoctorId()==doctorId)
                .filter(el->( el.getWeek()>=startWeek && el.getWeek()<=endWeek ))
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

        int total = stavka + accural + award - penalty;
        dto.setTotal(total);

        int actual = total - recd;
        dto.setActual(actual);

        return dto;
    }

    // итог по всем врачам за период
    public List<SalaryDTO> getSummarySalaryList(LocalDate from, LocalDate to) {
        List<SalaryDTO> list = new ArrayList<>();
        doctorService.getAll().stream().forEach(
                doctor -> {
                    list.add(this.getDoctorSummarySalary(doctor.getId()));
                }
        );
        return list;
    }



}
