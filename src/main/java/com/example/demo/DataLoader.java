package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;

    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {

        employeeRepository.save(new Employee(null, "Chubby", "Developer")).block();
        employeeRepository.save(new Employee(null, "Hippo", "Developer")).block();
    }
}
