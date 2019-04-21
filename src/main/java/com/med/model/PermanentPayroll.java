package com.med.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by george on 15.02.19.
 */
public class PermanentPayroll {

    private LocalDate from; // с какого числа включительно, по умолч. - с начала месяца
    private LocalDate to;   // по какое число включительно
    private String name; // фамилия доктора
    private int doctorId;// его айдишник
    private int days;    // сколько рабочих дней проработал за это время
    private int stavka;  // ставка  минус налог минус обед
    private int accural; // бонусы за процедуры за это время
    private int award;   // премии,за это время
    private int penalty; // также и штрафы
    private int total;   // всего начислено за это время
    private int recd;    // всего уже получено в кассе за это время
    private int actual;  // остаток на ТО время
    private int actualNow;  // остаток на сегодня

    public PermanentPayroll() {}

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStavka() {
        return stavka;
    }

    public void setStavka(int stavka) {
        this.stavka = stavka;
        this.recalculate();
    }

    public int getAccural() {
        return accural;
    }

    public void setAccural(int accural) {
        this.accural = accural;
        this.recalculate();
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
        this.recalculate();
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
        this.recalculate();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecd() {
        return recd;
    }

    public void setRecd(int recd) {
        this.recd = recd;
        this.recalculate();
    }

    public int getActual() {
        return actual;
    }

    public int getActualNow() {
        return actualNow;
    }

    public void setActualNow(int actualNow) {
        this.actualNow = actualNow;
    }

    private void recalculate(){
        this.total = this.stavka + this.accural + this.award
                - this.penalty;
        this.actual = this.total - this.recd;
    }

    @Override
    public String toString() {
        return "PermanentPayroll{" +
                "from=" + from +
                ", to=" + to +
                ", name='" + name + '\'' +
                ", doctorId=" + doctorId +
                ", days=" + days +
                ", stavka=" + stavka +
                ", accural=" + accural +
                ", award=" + award +
                ", penalty=" + penalty +
                ", total=" + total +
                ", recd=" + recd +
                ", actual=" + actual +
                ", actualNow=" + actualNow +
                '}';
    }
}
