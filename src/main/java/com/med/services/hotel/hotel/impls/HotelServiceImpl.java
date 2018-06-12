package com.med.services.hotel.hotel.impls;

import com.med.model.hotel.Hotel;
import com.med.repository.hotel.HotelRepository;
import com.med.services.hotel.hotel.interfaces.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<Hotel> saveAll(List<Hotel> hotels) {

        return repository.saveAll(hotels);
    }
    @Override
    public List<Hotel> getAll() {
        return repository.findAll();
    }

    @Override
    public Hotel getHotel(String hotelId) {
        return repository.findById(hotelId).orElse(null);
    }
}
