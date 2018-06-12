package com.med.services.hotel.hotel.interfaces;

import com.med.model.hotel.Hotel;

import java.util.List;

/**
 * Created by george on 01.06.18.
 */
public interface IHotelService {

    Hotel createHotel(Hotel hotel);
    List<Hotel> getAll();
    Hotel getHotel(String hotelId);
}
