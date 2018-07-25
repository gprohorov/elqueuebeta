package com.med.model.statistics.dto.accounting;

/**
 * Created by george on 22.07.18.
 */
public class AvailableexecutedPart {
    private Long available;
    private Long executed;
    private int percentage;

    public AvailableexecutedPart() {
    }

    public AvailableexecutedPart(Long available, Long executed, int percentage) {
        this.available = available;
        this.executed = executed;
        this.percentage = percentage;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getExecuted() {
        return executed;
    }

    public void setExecuted(Long executed) {
        this.executed = executed;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
