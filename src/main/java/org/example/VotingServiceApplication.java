package org.example;

import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Set;

import static org.example.model.Role.USER;

@EntityScan({"org.example.model"})
@SpringBootApplication
@AllArgsConstructor
public class VotingServiceApplication implements ApplicationRunner {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(VotingServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        userRepository.save(new User("guest@gmail.com", "User_First", "User_Last", "password", Set.of(USER)));
    }
}
