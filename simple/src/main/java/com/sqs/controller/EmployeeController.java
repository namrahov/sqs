package com.sqs.controller;

import com.sqs.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public void sendMessageToEmployee() {
        employeeService.sendMessageToEmployee();
    }

    @GetMapping
    public void receiveMessage() {
        employeeService.receiveMessage();
    }
}
