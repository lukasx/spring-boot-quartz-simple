package com.lksiezop.scheduler;

import com.lksiezop.logging.SimpleLogFacade;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
class QuartzConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    JobDetailFactoryBean simpleLogJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SimpleLogJob.class);
        jobDetailFactory.setName(SimpleLogJob.NAME);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity(SimpleLogJob.NAME)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever( 10 ))
                .build();
    }

    @Bean
    SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        return schedulerFactory;
    }

    @Bean
    SchedulerManager schedulerManager(Scheduler scheduler){
        return new SchedulerManager(scheduler);
    }

}
