package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return arg -> {
            var bob = new Student("Bob", "bob@gmail.com", LocalDate.of(2000, Month.JANUARY, 21));
            var john = new Student("John", "john@gmail.com", LocalDate.of(2001, Month.JANUARY, 21));

            repository.saveAll(List.of(bob, john));
        };
    }
}
