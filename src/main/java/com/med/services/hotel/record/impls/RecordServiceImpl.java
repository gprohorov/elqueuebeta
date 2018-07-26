package com.med.services.hotel.record.impls;

import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.hotel.dto.HotelDay;
import com.med.model.hotel.dto.KoikaLine;
import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;
import com.med.model.hotel.State;
import com.med.model.hotel.dto.RecordDto;
import com.med.repository.hotel.RecordRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.interfaces.IRecordService;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by george on 01.06.18.
 */
@Service
public class RecordServiceImpl implements IRecordService {

    private  List<Record> records;

    @Autowired
    RecordRepository repository;

    @Autowired
    KoikaServiceImpl koikaService;

    @Autowired
    AccountingServiceImpl accountingService;

    @Autowired
    PatientServiceImpl patientService;

    @Override
    public Record createRecord(Record record) {
        return repository.save(record);
    }


    public Record createRecordFromDto(@Valid RecordDto recordDto) {
        Record record = new Record();
        record.setPatientId(recordDto.getPatientId());
        record.setKoika(koikaService.getKoika(recordDto.getKoikaId()));
        record.setDesc(recordDto.getDesc());
        record.setStringStart(recordDto.getStart());
        record.setStringFinish(recordDto.getFinish());
        record.setPrice(recordDto.getPrice());
        record.setState(recordDto.getState());

        return this.createRecord(record);
    }

    @Override
    public Record updateRecord(Record record) {
       return repository.save(record);
    }

    public Record closeRecord(String patientId, PaymentType paymentType) {
        Record record = repository.findByPatientId(patientId).stream()
                .filter(record1 -> record1.getState() == State.OCCUP)
                .limit(1)
                .collect(Collectors.toList()).get(0);
        record.setFinish(LocalDateTime.now());
        record.setState(State.CLOSED);
        accountingService.createAccounting(new Accounting(record.getPatientId(), LocalDateTime.now(),
                getSum(record), paymentType, record.getKoika().getId(), record.getDesc()));
       return repository.save(record);
    }

    private int getSum(Record record){
        int days = (int)ChronoUnit.DAYS.between(record.getStart(), record.getFinish());
        int subtractPausedDays = 0;
        int sum = 0;
        Optional<List<Record>> pausedRecordsOpt = getPausedRecordsFromTo(record.getPatientId(), record.getStart().toLocalDate(),
                record.getFinish().toLocalDate());
        if(pausedRecordsOpt.isPresent()){
            List<Record> pausedRecords = pausedRecordsOpt.get();
            for (Record r : pausedRecords){
                if(r.getFinish().isAfter(LocalDateTime.now())){
                    r.setFinish(LocalDateTime.now());
                }
                r.setState(State.CLOSED);
                repository.save(r);
                subtractPausedDays += (int)ChronoUnit.DAYS.between(r.getStart(), r.getFinish());
            }
        }
        sum = (days - subtractPausedDays) * record.getPrice();
        record.setDesc(formDescForClosedRecord(record.getKoika().getName(), days, subtractPausedDays, record.getPrice(), sum));
        return -sum;
    }

    private String formDescForClosedRecord(String koikaName, int recordDays, int pausedRecordDays,
                                           int recordPrice, int sum){
        return "Ліжко " + koikaName + ": ("+recordDays +"[дні] - " + pausedRecordDays
                + "[дні на паузі]) * " + recordPrice + "[ціна] = " + sum + "[сума]";
    }

    private Optional<List<Record>> getPausedRecordsFromTo(String patientId, LocalDate from, LocalDate to){
        return Optional.ofNullable(repository.findByPatientId(patientId).stream()
                .filter(record->record.getStart().toLocalDate().isAfter(from.minusDays(1)))
                .filter(record->record.getStart().toLocalDate().isBefore(to.plusDays(1)))
                .filter(record1 -> record1.getState() == State.PAUSED)
                .collect(Collectors.toList()));
    }

    public Record relocatePatient(Record record, PaymentType paymentType){     //get paymentType from Record?
        Record oldRecord = repository.findByPatientId(record.getPatientId()).stream()
                .filter(record1 -> record1.getState() == State.OCCUP)
                .limit(1)
                .collect(Collectors.toList()).get(0);
        if  (oldRecord.getPrice() < record.getPrice()) {
            oldRecord.setFinish(oldRecord.getFinish().minusDays(1));
        } else {
            record.setStart(record.getStart().plusDays(1));
        }
        closeRecord(oldRecord.getPatientId(), paymentType);
        return repository.save(record);
    }

    public List<Record> saveAll(List<Record> records) {
        return repository.saveAll(records);
    }

    @Override
    public List<Record> getAll() {
        return repository.findAll();
    }

    @Override
    public Record getRecord(String recordId) {
        return repository.findById(recordId).orElse(null);
    }

    @Override
    public List<Record> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish) {
        return repository.findByPatientId(patientId).stream()
                .filter(record->record.getFinish() != null)
                .filter(record->record.getStart().toLocalDate().isAfter(start.minusDays(1)))
                .filter(record->record.getFinish().toLocalDate().isBefore(finish.plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public Record findByKoikaAndDate(Koika koika, LocalDateTime localDateTime) {
        return repository.findByKoika(koika).stream()
                .filter(record -> record.getStart().isBefore(localDateTime))
                .filter(record -> record.getFinish().isAfter(localDateTime))
                .findFirst().orElse(null);
    }

    public List<Record> getAllForKoikaFromTo(Koika koika, LocalDate start, LocalDate finish) {
        return repository.findByKoika(koika).stream()
                .filter(record -> record.getFinish() != null)
                .filter(record->record.getFinish().toLocalDate().isAfter(start.minusDays(1)))
                .filter(record->record.getStart().toLocalDate().isBefore(finish.plusDays(1)))
                .collect(Collectors.toList());
    }

    public List<Koika> getFreeKoikasForDay(LocalDateTime localDateTime){
        List<Koika> freeKoikas = koikaService.getAll();
        List<Koika> notFreeKoikas = new ArrayList<>();
        for (Koika koika : freeKoikas){
            if (findByKoikaAndDate(koika, localDateTime) != null){
                notFreeKoikas.add(koika);
            }
        }
        freeKoikas.removeAll(notFreeKoikas);
        return freeKoikas;
    }

    //all Koikas from repository version
    public List<KoikaLine> getLines(int days){
        List<KoikaLine> koikaLines = new ArrayList<>();
        LocalDate endDate = LocalDate.now().plusDays(days);
        List<Koika> allKoikas = koikaService.getAll();
       // allKoikas.stream().sorted(Comparator.comparing(koika -> koika.getChamber().getName())).collect(Collectors.toList())
        Collections.sort(allKoikas, (a, b) -> a.compareTo(b));
/*        allKoikas.stream().sorted(Comparator.comparing(koika -> koika.getChamber().getName()))

                .collect(Collectors.toList());*/
        for (Koika koika : allKoikas){
            List<HotelDay> koikaHotelDays = new ArrayList<>();
            LocalDate dateWithFreeState = LocalDate.now();
            List<Record> recordsForKoika = getAllForKoikaFromTo(koika, LocalDate.now(),
                    LocalDate.now().plusDays(days));
            if (recordsForKoika != null) {
                Collections.sort(recordsForKoika, Comparator.comparing(Record::getStart));
                for (Record record : recordsForKoika) {
                    LocalDate startDay = record.getStart().toLocalDate();
     /*??????*/     koika.setPatient(patientService.getPatient(record.getPatientId()));
                    if (startDay.isBefore(LocalDate.now()))
                    {
                        startDay = LocalDate.now();
                    }
                    LocalDate finishDay = record.getFinish().toLocalDate();
                    if (finishDay.isAfter(endDate))
                    {
                        finishDay = endDate;
                    }
                    while (dateWithFreeState.isBefore(startDay)){
                        koikaHotelDays.add(new HotelDay(dateWithFreeState, State.FREE));   // consider adding another state from Koika document
                        dateWithFreeState = dateWithFreeState.plusDays(1);
                    }
                    State stateFromRecord = record.getState();
                    while (!startDay.equals(finishDay)) {
                        koikaHotelDays.add(new HotelDay(startDay, stateFromRecord));
                        startDay = startDay.plusDays(1);
                    }
                    dateWithFreeState = finishDay;
                }
            }
            while (dateWithFreeState.isBefore(endDate)){
                koikaHotelDays.add(new HotelDay(dateWithFreeState, State.FREE));   // consider adding another state from Koika document
                dateWithFreeState = dateWithFreeState.plusDays(1);
            }
            koikaLines.add(new KoikaLine(koika, koikaHotelDays));
        }
        return koikaLines;
    }
}
