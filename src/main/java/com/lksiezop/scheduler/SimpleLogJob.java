package com.lksiezop.scheduler;

import com.lksiezop.logging.SimpleLogFacade;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
class SimpleLogJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(SimpleLogJob.class);

    public static final String NAME = "SimpleLogJob";
    public static final String ENTRY = "ENTRY";

    private SimpleLogFacade simpleLogFacade;

    @Autowired
    void setSimpleLogFacade(SimpleLogFacade simpleLogFacade) {
        this.simpleLogFacade = simpleLogFacade;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        if(jobDataMap.containsKey(SimpleLogJob.ENTRY)){
            Object o = jobDataMap.get(SimpleLogJob.ENTRY);
            simpleLogFacade.log(o.toString());
        }else {
            simpleLogFacade.log("Simple log");
        }


        logger.info("Job finished");


    }


}
