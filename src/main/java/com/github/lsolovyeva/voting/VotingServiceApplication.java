package com.github.lsolovyeva.voting;

import com.github.lsolovyeva.voting.model.User;
import com.github.lsolovyeva.voting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

import static com.github.lsolovyeva.voting.model.Role.USER;

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
