package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

// зкземляр этого класса в жизни является строкой  ежедневной зарплатной ведомости.
// По какому какому-то врачу.
// Сегодняшняя ставка + бонусы за сегодня  + остаток со вчера - обед - налог - что-то уже взялн
//  Грубо говоря,  зарплатная ведомость на каждый день по 1 врачу
//  ставка начисляется из расчета мясячная ставка /30  вне зависимости от рабочих часов

@Document
public class SalaryDaily {

    @Id
    private String id;
    private LocalDate date; // дата
    private String name; // фамилия доктора
    private int doctorId;// его айдишник

    private int stavka; // ставка
  //  private int taxes;  // налог
 //   private int canteen;  // обед
    private int bonuses; // бонусы за процедуры
  //  private int award;   // премии, могут добавляться в течение дня
 //   private int penalty; // также и штрафы
    private int total;   // всего начислено за день
 //   private int recd;    // всего уже получено в кассе за текущий день
  //  private int rest;    // остаток в кассе, не выбранный за всё время
   // private int actual;  // сумма, которую можно получить в кассе, учитывая выше изложенное (не влезая в долг)

    public SalaryDaily() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getStavka() {return stavka;
    }

    public void setStavka(int stavka) {
        this.stavka = stavka;
        this.reacalculate();
    }

    public int getBonuses() {
        return bonuses;
    }

    public void setBonuses(int bonuses) {
        this.bonuses = bonuses;
        this.reacalculate();
    }

    public int getTotal() {
        return total;
    }

    private void reacalculate(){
        this.total = this.stavka + this.bonuses;
    }

}