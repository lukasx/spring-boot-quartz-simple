package com.lksiezop.scheduler.dto;


public class RescheduleLogJobRequest {
    private String cron;

    public RescheduleLogJobRequest() {
    }

    public RescheduleLogJobRequest(String cron) {
        this.cron = cron;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
