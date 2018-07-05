package com.med.services.hotel.hotel.impls;

import com.med.model.hotel.Hotel;
import com.med.repository.hotel.HotelRepository;
import com.med.services.hotel.hotel.interfaces.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 01.06.18.
 */
@Service
public class HotelServiceImpl implements IHotelService{

    private  List<Hotel> hotels;

    @Autowired
    HotelRepository repository;

    @Override
    public Hotel createHotel(Hotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
       return repository.save(hotel);
    }


    public List<Hotel> saveAll(List<Hotel> hotels) {

        return repository.saveAll(hotels);
    }
    @Override
    public List<Hotel> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Hotel> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish) {

        return repository.findByPatientId(patientId).stream()
                .filter(hotel->hotel.getFinish() != null)
                .filter(hotel->hotel.getStart().toLocalDate().isAfter(start.minusDays(1)))
                .filter(hotel->hotel.getFinish().toLocalDate().isBefore(finish.plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public Hotel getHotel(String hotelId) {
        return repository.findById(hotelId).orElse(null);
    }
}
