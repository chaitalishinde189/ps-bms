package com.ps.thm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = {"com.ps.thm"})
@Slf4j
public class TheatreManagementApplication {

    public static void main(String[] args) {
        log.info("Starting Theatre Management application...");
        SpringApplication.run(TheatreManagementApplication.class, args);
    }

}
