package com.med.services.salarydto.impls;

import com.med.model.*;
import com.med.repository.salary.SalaryRepository;
import com.med.repository.salarydto.SalaryDTORepository;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import com.med.services.salarydto.interfaces.ISalaryDTOService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    // главврач генерит зарплату врачу за неделю. Если его что-то не устраивает,
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

    public List<SalaryDTO> createNewTable(){
        LocalDate today = LocalDate.now();
        List<SalaryDTO> list =
                this.generateSalaryWeekTable(today.minusDays(6), today.plusDays(1));
        List<SalaryDTO> expiredList =repository.findAll().stream()
                .filter(row->row.getClosed()==null)
                .collect(Collectors.toList());
         expiredList.stream().forEach(row->row.setClosed(LocalDateTime.now()));
         repository.saveAll(expiredList);
        return repository.saveAll(list);
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

}
