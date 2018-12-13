package com.lksiezop.scheduler.dto;

public enum  ScheduleLogResponse {

    OK("OK"),ERROR("ERROR");

    private String message;

    ScheduleLogResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
