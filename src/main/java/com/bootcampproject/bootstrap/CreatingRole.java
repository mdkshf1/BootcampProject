package com.bootcampproject.bootstrap;

import com.bootcampproject.BootcampprojectApplication;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.logging.Logger;

public class CreatingRole implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger(BootcampprojectApplication.class);
        logger.info("this  is run method of application interface");
    }
}
