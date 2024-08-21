package com.coma.coma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ComaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComaApplication.class, args);
    }

}
