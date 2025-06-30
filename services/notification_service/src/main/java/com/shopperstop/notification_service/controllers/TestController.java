package com.shopperstop.notification_service.controllers;

import com.shopperstop.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class TestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test-email")
    public ResponseEntity<?> sendTestEmail() {

        try {
            emailService.setJavaMailSender("mananpd2526@gmail.com", "Test Subject", "Test Body");
            return new ResponseEntity<String>("Mail sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
