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

//@EnableJpaRepositories("org.example.repository")
@EntityScan({"org.example.model"}) //, "org.example.service"
@SpringBootApplication
@AllArgsConstructor
public class VotingServiceApplication implements ApplicationRunner {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(VotingServiceApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }*/

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(new User("guest@gmail.com", "User_First", "User_Last", "password", Set.of(USER)));
        //userRepository.save(new User("admin@javaops.ru", "Admin_First", "Admin_Last", "admin", Set.of(USER, ADMIN)));
        /*System.out.println("*********START*********");
        System.out.println(userRepository.findAll());
        System.out.println("*********END*********");
        System.out.println(userRepository.findByLastNameContainingIgnoreCase("last"));
*/
    }
}
