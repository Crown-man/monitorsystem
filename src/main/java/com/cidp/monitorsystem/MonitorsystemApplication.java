package com.cidp.monitorsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cidp.monitorsystem.mapper")
@EnableAsync
@EnableScheduling
public class MonitorsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorsystemApplication.class, args);
    }

}
