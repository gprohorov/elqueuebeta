package com.med.services.hotel.koika.interfaces;

import com.med.model.hotel.Koika;

import java.util.List;

/**
 * Created by george on 01.06.18.
 */
public interface IKoikaService {

    Koika createKoika(Koika koika);
    List<Koika> getAll();
    Koika getKoika(int koikaId);
}
