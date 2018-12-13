package com.lksiezop.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoggingConfiguration {

    @Bean
    SimpleLogFacade simpleLogFacade(){
        return new SimpleLogFacade();
    }

}
