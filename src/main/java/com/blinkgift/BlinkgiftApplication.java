package com.blinkgift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BlinkgiftApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlinkgiftApplication.class, args);
    }

}
