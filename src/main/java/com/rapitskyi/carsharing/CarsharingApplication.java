package com.rapitskyi.carsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CarsharingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarsharingApplication.class, args);
    }
}
