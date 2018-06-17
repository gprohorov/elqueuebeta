package com.med.services.hotel.hotel.interfaces;

import com.med.model.hotel.Hotel;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 01.06.18.
 */
public interface IHotelService {

    Hotel createHotel(Hotel hotel);
    List<Hotel> getAll();
    List<Hotel> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish);
    Hotel getHotel(String hotelId);

}
