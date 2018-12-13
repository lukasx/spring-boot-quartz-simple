package com.lksiezop.scheduler;

import com.lksiezop.scheduler.dto.RescheduleLogJobRequest;
import com.lksiezop.scheduler.dto.RescheduleLogJobResponse;
import com.lksiezop.scheduler.dto.ScheduleLogRequest;
import com.lksiezop.scheduler.exception.JobReschedulingException;
import com.lksiezop.scheduler.exception.JobSchedulingException;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;


class SchedulerManager {

    private final Scheduler scheduler;

    SchedulerManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    void scheduleLog(ScheduleLogRequest scheduleLogRequest) throws JobSchedulingException {
        JobDetail jobDetail = createJobDetail(scheduleLogRequest);
        Trigger trigger = createJobTrigger(jobDetail);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new JobSchedulingException("Could not schedule log", e);
        }
    }


    RescheduleLogJobResponse rescheduleLogJob(RescheduleLogJobRequest rescheduleLogJobRequest) throws JobReschedulingException {

        try {
            final TriggerKey triggerKey = triggerKey(SimpleLogJob.NAME);
            Trigger oldTrigger = scheduler.getTrigger(triggerKey);
            final Trigger newTrigger = createNewTrigger(oldTrigger, rescheduleLogJobRequest.getCron());
            LocalDateTime localDateTime = Optional.ofNullable(scheduler.rescheduleJob(triggerKey, newTrigger))
                    .map(Date::toInstant)
                    .map(i->LocalDateTime.ofInstant(i, ZoneId.systemDefault()))
                    .orElse(LocalDateTime.now());
            return new RescheduleLogJobResponse(localDateTime);
        } catch (Exception e) {
            throw new JobReschedulingException("Could not reschedule log job", e);
        }
    }

    private Trigger createNewTrigger(Trigger oldTrigger, String cron) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        Trigger trigger = newTrigger()
                .withIdentity(SimpleLogJob.NAME)
                .withSchedule(cronScheduleBuilder)
                .startNow()
                .build();

        return trigger;
    }


    private JobDetail createJobDetail(ScheduleLogRequest scheduleLogRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SimpleLogJob.ENTRY, scheduleLogRequest.getEntry());
        return JobBuilder.newJob(SimpleLogJob.class)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger createJobTrigger(JobDetail jobDetail) {
        return newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName())
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

}
