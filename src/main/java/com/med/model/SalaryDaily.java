package com.med.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// зкземляр этого класса в жизни является строкой  ежедневной зарплатной ведомости.
// По какому какому-то врачу.
// Сегодняшняя ставка + бонусы за сегодня  + остаток со вчера - обед - налог - что-то уже взялн
// Грубо говоря,  зарплатная ведомость на каждый день по 1 врачу
// ставка начисляется из расчета мясячная ставка /30  вне зависимости от рабочих часов

@Document
public class SalaryDaily {

    @Id
    private String id;
    private LocalDate date; // дата
    private LocalDate from; // дата
    private int days;

    private String name; 	// фамилия доктора
    private int doctorId;	// его айдишник
    private int stavka; 	// ставка
    private int hours;

    private int bonuses; 	// бонусы за процедуры

    private int total;   	// всего начислено за день


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

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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

    private void reacalculate() {
        this.total = this.stavka + this.bonuses;
    }

    @Override
    public String toString() {
        return "SalaryDaily{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", from=" + from +
                ", days=" + days +
                ", name='" + name + '\'' +
                ", doctorId=" + doctorId +
                ", stavka=" + stavka +
                ", hours=" + hours +
                ", bonuses=" + bonuses +
                ", total=" + total +
                '}';
    }
}
