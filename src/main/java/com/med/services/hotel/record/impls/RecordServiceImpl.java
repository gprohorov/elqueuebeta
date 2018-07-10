package com.med.services.hotel.record.impls;

import com.med.model.dto.HotelDay;
import com.med.model.dto.KoikaLine;
import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;
import com.med.model.hotel.State;
import com.med.repository.hotel.RecordRepository;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.interfaces.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public Record createRecord(Record record) {
        return repository.save(record);
    }

    @Override
    public Record updateRecord(Record record) {
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
    public List<Record> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish) {

        return repository.findByPatientId(patientId).stream()
                .filter(record->record.getFinish() != null)
                .filter(record->record.getStart().toLocalDate().isAfter(start.minusDays(1)))
                .filter(record->record.getFinish().toLocalDate().isBefore(finish.plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public Record getRecord(String recordId) {
        return repository.findById(recordId).orElse(null);
    }
    public List<Record> getAllForKoikaFromTo(Koika koika, LocalDate start, LocalDate finish) {

        return repository.findByKoika(koika).stream()
                .filter(record -> record.getFinish() != null)
                .filter(record->record.getStart().toLocalDate().isAfter(start.minusDays(1)))
                .filter(record->record.getFinish().toLocalDate().isBefore(finish.plusDays(1)))
                .collect(Collectors.toList());
    }

    //all Koikas from repository version
    public List<KoikaLine> getLines(int date){

        List<KoikaLine> koikaLines = new ArrayList<>();
        List<HotelDay> koikaHotelDays = new ArrayList<>();
        LocalDate dateWithFreeState = LocalDate.now();;
        LocalDate endDate = LocalDate.now().plusDays(date);
        List<Koika> allKoikas = koikaService.getAll();

        for (Koika koika : allKoikas){
            List<Record> recordsForKoika = getAllForKoikaFromTo(koika, LocalDate.now(),
                    LocalDate.now().plusDays(date));
            if (recordsForKoika != null) {
                Collections.sort(recordsForKoika, Comparator.comparing(Record::getStart));
                for (Record record : recordsForKoika) {
                    LocalDate startDay = record.getStart().toLocalDate();
                    LocalDate finishDay = record.getFinish().toLocalDate();
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
