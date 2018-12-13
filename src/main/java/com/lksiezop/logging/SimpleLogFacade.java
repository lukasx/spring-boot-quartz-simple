package com.lksiezop.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleLogFacade {

    private static final Logger logger = LoggerFactory.getLogger(SimpleLogFacade.class);

    public void log(String entry){

        logger.info("{}", entry);
    }
}
