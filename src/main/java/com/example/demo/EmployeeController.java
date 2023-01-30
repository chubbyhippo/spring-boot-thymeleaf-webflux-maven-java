package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public Mono<Rendering> index() {
        return employeeRepository
                .findAll()
                .collectList()
                .map(employees -> Rendering
                        .view("index")
                        .modelAttribute("employees", employees)
                        .modelAttribute("newEmployee", new Employee(null, "", ""))
                        .build());
    }

    @PostMapping("/new-employee")
    public Mono<String> newEmployee(@ModelAttribute Mono<Employee> newEmployee) {
        return newEmployee
                .publishOn(Schedulers.boundedElastic())
                .map(employee -> {
                    employeeRepository
                            .save(new Employee(null, employee.name(), employee.role()))
                            .block();
                    return "redirect:/";
                });
    }
}
