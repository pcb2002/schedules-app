package com.schedulesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchedulesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulesAppApplication.class, args);
    }

}
