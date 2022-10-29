package com.sqs.controller;

import com.sqs.service.JMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jms")
@RequiredArgsConstructor
public class JMSController {

    private final JMSService jmsService;

    @PostMapping
    public void postJMSMessage() {
        jmsService.postJMSMessage();
    }

    @GetMapping
    public void consumeJMSMessage() {
        jmsService.consumeJMSMessage();
    }
}
