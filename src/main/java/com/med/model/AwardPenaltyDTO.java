package com.med.model;

/**
 * Created by george on 31.10.18.
 */
public class AwardPenaltyDTO {

    private int doctorID;
    private int award;
    private int penalty;

    public AwardPenaltyDTO(int doctorID, int award, int penalty) {
        this.doctorID = doctorID;
        this.award = award;
        this.penalty = penalty;
    }

    public AwardPenaltyDTO() {
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

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    @Override
    public String toString() {
        return "AwardPenaltyDTO{" +
                "doctorID=" + doctorID +
                ", award=" + award +
                ", penalty=" + penalty +
                '}';
    }
}
