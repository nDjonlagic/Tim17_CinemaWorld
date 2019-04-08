package net.springboot.user_micro_service;

import java.util.Arrays;

import javax.swing.text.html.parser.Entity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.springboot.user_micro_service.model.Role;
import net.springboot.user_micro_service.model.User;
import net.springboot.user_micro_service.repository.UserRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
    public CommandLineRunner demoData(UserRepository repo) {
        return args -> { 
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            repo.save(new User("Ned","Ned2","ndjonlagic1@gmail.com",passwordEncoder.encode("admin"),Arrays.asList(new Role("ROLE_ADMIN"))));
        };
    }
}

