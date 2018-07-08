package com.med.datastorage;

import com.med.model.Card;
import com.med.services.card.impls.CardServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 13.05.18.
 */
@Component
public class CardStorage {

   @Autowired
   ProcedureServiceImpl procedureService;

    @Autowired
    CardServiceImpl service;


    @PostConstruct
    void init(){
/*
        service.getFreeProcedures().stream().forEach(

                el-> {
                    System.out.println(procedureService.getProcedure(el).getName());
                }
        );

*/



      service.saveAll(cards);
        }

        List<Card> cards = new ArrayList<>(

                Arrays.asList
                ( new Card(2,2,"Діагностика",           1, true
                        , new ArrayList<Integer>()
                               , new ArrayList<Integer>()
                               , new ArrayList<Integer>(Arrays.asList()))

                , new Card(3,3,"Мануальна терапія",     1, true
                        , new ArrayList<Integer>()
                               , new ArrayList<Integer>()
                               , new ArrayList<Integer>(Arrays.asList()))

       /*?*/    , new Card(4,4,"Суха витяжка",          1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

       /*?*/    , new Card(5,5,"Механічний масаж",      1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList(17,20,18,25)))

                , new Card(6,6,"Ручний масаж",          1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(7,7,"Ультразвук",            1, true
                        , new ArrayList<Integer>(Arrays.asList(14,16,21))
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(8,8,"Магніто-терапія",       1, true
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(9,9,"Ударно-хв. терапія",    3, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(10,10,"Пресо-терапія",       1, true
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(11,11,"Інфра-червона сауна", 1, true
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(12,12,"Тепловізор",          1, true
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))

                , new Card(13,13,"Вигрівання",          1, true
                        , new ArrayList<Integer>(Arrays.asList(18))
                                , new ArrayList<Integer>(Arrays.asList(4,5,6,27,9,5,6))
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(14,14,"Лазер",               1, true
                        , new ArrayList<Integer>(Arrays.asList(7))
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(15,15,"HiTop-терапіяя",      1, false
                        , new ArrayList<Integer>(Arrays.asList())
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(16,16,"Hilt-терапія",        1, true
                        , new ArrayList<Integer>(Arrays.asList(7))
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(17,17,"Підводна витяжка",    1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList(5,6,22))
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(18,18,"Локальна кріо-терапія", 1, true
                        , new ArrayList<Integer>(Arrays.asList())
                                , new ArrayList<Integer>(Arrays.asList(27,9,5,6,13,24,4,17,20,25,26,28))
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(19,19,"Електро-терапія",       1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(20,20,"Гідро-масаж",           1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList(5,6,22))
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(21,21,"MLS",                   1, true
                        , new ArrayList<Integer>(Arrays.asList(7))
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(22,22,"УЗ діагностика", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(23,23,"ЕКГ", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(24,24,"Тритон", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(25,25,"Озономатік", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList(5,6,22))
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(26,26,"Пасивна кінезотерапія", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(27,27,"Прицільна уд.-хв. терапія", 3, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(28,28,"Редкорд",                   1, true
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList(24,4,17))
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(29,29,"Механотерапія плечового суглобу", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(30,30,"Механотерапія ліктьового суглобу", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(31,31,"Механотерапія кульшового суглобу", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(32,32,"Механотерапія колінного суглобу", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))
                , new Card(33,33,"Пропріорецептивна нейром'язова фасилітація", 1, false
                        , new ArrayList<Integer>()
                                , new ArrayList<Integer>()
                                , new ArrayList<Integer>(Arrays.asList()))









                )



        );


}
