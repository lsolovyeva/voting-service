package com.github.lsolovyeva.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VotingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VotingServiceApplication.class, args);
    }
}
