package com.lksiezop.scheduler.dto;


import java.time.LocalDateTime;

public class RescheduleLogJobResponse {

    private LocalDateTime nextExecution;


    public RescheduleLogJobResponse(LocalDateTime nextExecution) {
        this.nextExecution = nextExecution;
    }

    public LocalDateTime getNextExecution() {
        return nextExecution;
    }

    public void setNextExecution(LocalDateTime nextExecution) {
        this.nextExecution = nextExecution;
    }

}
