package com.lksiezop.scheduler;

import com.lksiezop.scheduler.dto.RescheduleLogJobRequest;
import com.lksiezop.scheduler.dto.RescheduleLogJobResponse;
import com.lksiezop.scheduler.dto.ScheduleLogRequest;
import com.lksiezop.scheduler.dto.ScheduleLogResponse;
import com.lksiezop.scheduler.exception.JobReschedulingException;
import com.lksiezop.scheduler.exception.JobSchedulingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
class JobSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(JobSchedulerController.class);


    private final SchedulerManager schedulerManager;

    @Autowired
    JobSchedulerController(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    @PutMapping("/log")
    ResponseEntity<ScheduleLogResponse> logImmediate(@RequestBody @Valid ScheduleLogRequest scheduleLogRequest) {
        try {
            schedulerManager.scheduleLog(scheduleLogRequest);
            return ResponseEntity.ok(ScheduleLogResponse.OK);
        } catch (JobSchedulingException e) {
            return ResponseEntity.ok(ScheduleLogResponse.ERROR);
        }
    }

    @PutMapping("/rescheduleLogger")
    ResponseEntity<RescheduleLogJobResponse> rescheduleLogJob(@RequestBody @Valid RescheduleLogJobRequest rescheduleLogJobRequest) throws JobReschedulingException{
        return ResponseEntity.ok(schedulerManager.rescheduleLogJob(rescheduleLogJobRequest));
    }

}
