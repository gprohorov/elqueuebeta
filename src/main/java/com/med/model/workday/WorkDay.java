package com.med.model.workday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WorkDay {

    @Id
    private String id;

    private LocalDate date;
    LocalDateTime start;                    // приняли первого пациента
    LocalDateTime finish;                   // отправили последнего
    private int assignedPatients; 			// пациенты, которые зписались на сегодня
    private int activePatients;   			// которые пришли

    private int sumAtStart;  				// сумма в кассе на начало рабочего дня
    private int sumForExecutedProcedures; 	// наделали процедур на эту сумму
    private int cash;      					// кеш за сегодня
    private int card;     					// карточка
    private int discount;  					// знижки
    private int outlay;   					// расходы
    private int cashierWithdrawal;  		// изъяли из касы
    private int sumAtFinish;    			// что осталось в кассе после выемки

    private int debtOfTodayAll;   			// сумма долга всех должников
    private int debtOfTodayWithoutHotel;   	// сумма долга всех не готельних
    private List<String> debtOfTodayWithoutHotelList;   	// сумма долга всех не готельних
    private int debtOfTodayActive;  		// сумма долга всех активных
    private int debtOfTodayPassive; 		// долг тех, кто был записан но не пришел
    private int debtOfHotel;        		// готельных
    private int debtOfTomorrowPassive;  	// долг сегодняшних активных не записаных на завтра
    private int doctorsActive;  			// сколько докторов сегодня въябывало
    private int doctorsAbsent; 				// сколько сачков
    private String doctorsAbsentList; 		// список сачков
    private int discountSum; 				// сколько дали знижок - сумма всего
    private String discountList; 		    // список знижок

    public int getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(int discountSum) {
        this.discountSum = discountSum;
    }

    public String getDiscountList() {
        return discountList;
    }

    public void setDiscountList(String discountList) {
        this.discountList = discountList;
    }

    public WorkDay(LocalDate date) {
        this.date = date;
    }

    public WorkDay() {
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public int getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(int assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    public int getActivePatients() {
        return activePatients;
    }

    public void setActivePatients(int activePatients) {
        this.activePatients = activePatients;
    }

    public int getSumAtStart() {
        return sumAtStart;
    }

    public void setSumAtStart(int sumAtStart) {
        this.sumAtStart = sumAtStart;
    }

    public int getSumForExecutedProcedures() {
        return sumForExecutedProcedures;
    }

    public void setSumForExecutedProcedures(int sumForExecutedProcedures) {
        this.sumForExecutedProcedures = sumForExecutedProcedures;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getOutlay() {
        return outlay;
    }

    public void setOutlay(int outlay) {
        this.outlay = outlay;
    }

    public int getCashierWithdrawal() {
        return cashierWithdrawal;
    }

    public void setCashierWithdrawal(int cashierWithdrawal) {
        this.cashierWithdrawal = cashierWithdrawal;
    }

    public int getSumAtFinish() {
        return sumAtFinish;
    }

    public void setSumAtFinish(int sumAtFinish) {
        this.sumAtFinish = sumAtFinish;
    }

    public int getDebtOfTodayAll() {
        return debtOfTodayAll;
    }

    public void setDebtOfTodayAll(int debtOfTodayAll) {
        this.debtOfTodayAll = debtOfTodayAll;
    }

    public int getDebtOfTodayActive() {
        return debtOfTodayActive;
    }

    public void setDebtOfTodayActive(int debtOfTodayActive) {
        this.debtOfTodayActive = debtOfTodayActive;
    }

    public int getDebtOfTodayPassive() {
        return debtOfTodayPassive;
    }

    public void setDebtOfTodayPassive(int debtOfTodayPassive) {
        this.debtOfTodayPassive = debtOfTodayPassive;
    }

    public int getDebtOfHotel() {
        return debtOfHotel;
    }

    public void setDebtOfHotel(int debtOfHotel) {
        this.debtOfHotel = debtOfHotel;
    }

    public int getDebtOfTomorrowPassive() {
        return debtOfTomorrowPassive;
    }

    public void setDebtOfTomorrowPassive(int debtOfTomorrowPassive) {
        this.debtOfTomorrowPassive = debtOfTomorrowPassive;
    }

    public int getDoctorsActive() {
        return doctorsActive;
    }

    public void setDoctorsActive(int doctorsActive) {
        this.doctorsActive = doctorsActive;
    }

    public int getDoctorsAbsent() {
		return doctorsAbsent;
	}

	public void setDoctorsAbsent(int doctorsAbsent) {
		this.doctorsAbsent = doctorsAbsent;
	}

	public String getDoctorsAbsentList() {
		return doctorsAbsentList;
	}

	public void setDoctorsAbsentList(String doctorsAbsentList) {
		this.doctorsAbsentList = doctorsAbsentList;
	}

    public int getDebtOfTodayWithoutHotel() {
        return debtOfTodayWithoutHotel;
    }

    public void setDebtOfTodayWithoutHotel(int debtOfTodayWithoutHotel) {
        this.debtOfTodayWithoutHotel = debtOfTodayWithoutHotel;
    }

    public List<String> getDebtOfTodayWithoutHotelList() {
        return debtOfTodayWithoutHotelList;
    }

    public void setDebtOfTodayWithoutHotelList(List<String> debtOfTodayWithoutHotelList) {
        this.debtOfTodayWithoutHotelList = debtOfTodayWithoutHotelList;
    }
}