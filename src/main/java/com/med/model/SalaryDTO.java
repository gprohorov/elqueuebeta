package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by george on 28.10.18.
 */

    // зкземляр этого класса в жизни является строкой зарплатной ведомости.
    // Эту ведомость передают из бухгалтерии в кассу для выдачи зарплаты.
    // Каждая строка соответствует какому-то врачу.
    // Ведомость охватывает ТРИ недели.
    // Прошлая неделя. За неё насчитывается: - ставка исходя из проработанных часов
    //                                       - начисления за процедуры
    // Позапрошлая неделя.  Если врач не выбрал зарплату за позапрошлую неделю, то
    // этот остаток ему переносится в в ведомость в колонку rest
    // Текущая неделя.  Можно врачу начислять премии и штрафы.  А также учитывать ,
    // сколько он уже из кассы выгреб.

    @Document
public class SalaryDTO {
    @Id
    private String id;
    private LocalDate from;  // с какого числа включительно
    private LocalDate to;   //  по какое число включительно считаем зарплату
    private LocalDateTime opened; // открыли зарплатную ведомость - можно выплачивать з/п
    private LocalDateTime closed;  // з/п выплачена, ведомость закрыта, остатки в след. вед.
    private String name;  // фамилия доктора
    private int doctorId;  // его айдишник
    private int days;     // сколько дней проработал (обычно 6 дней) за прошедшую неделю
    private int hours;    // часов за прошедшую неделю
    private int stavka;   // мин. начисления минус налог минус обед
    private int accural;   // бонусы за процедуры
    private int award;    // премии, могут добавляться в теч.текущей  недели
    private int penalty;  // также и штрафы
    private int kredit;   // сколько можно взять в долг
    private int total;   // всего начислено за прошедшую неделю
    private int recd;    // всего уже получено в кассе за текущую  неделю
    private int rest;    //  остаток в кассе, не выбранный зо позапрошлую неделю
    private int actual;  // сумма, которую можно получитьв кассе, учитывая вышеизложенное
                         // (не влезая в долг)
                        //

    public SalaryDTO(String name, int doctorId, int days, int hours, int stavka, int accural, int award, int penalty, int kredit, int total, int recd, int rest, int actual) {
        this.name = name;
        this.doctorId = doctorId;
        this.days = days;
        this.hours = hours;
        this.stavka = stavka;
        this.accural = accural;
        this.award = award;
        this.penalty = penalty;
        this.kredit = kredit;
        this.total = total;
        this.recd = recd;
        this.rest = rest;
        this.actual = actual;
    }

    public SalaryDTO() {
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

    public int getStavka() {
        return stavka;
    }

    public void setStavka(int stavka) {
        this.stavka = stavka;
    }

    public int getAccural() {
        return accural;
    }

    public void setAccural(int accural) {
        this.accural = accural;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getKredit() {
        return kredit;
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    public int getRecd() {
        return recd;
    }

    public void setRecd(int recd) {
        this.recd = recd;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDateTime getOpened() {
        return opened;
    }

    public void setOpened(LocalDateTime opened) {
        this.opened = opened;
    }

    public LocalDateTime getClosed() {
        return closed;
    }

    public void setClosed(LocalDateTime closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return "SalaryDTO{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", opened=" + opened +
                ", closed=" + closed +
                ", name='" + name + '\'' +
                ", doctorId=" + doctorId +
                ", days=" + days +
                ", hours=" + hours +
                ", stavka=" + stavka +
                ", accural=" + accural +
                ", award=" + award +
                ", penalty=" + penalty +
                ", kredit=" + kredit +
                ", total=" + total +
                ", recd=" + recd +
                ", rest=" + rest +
                ", actual=" + actual +
                '}';
    }
}
