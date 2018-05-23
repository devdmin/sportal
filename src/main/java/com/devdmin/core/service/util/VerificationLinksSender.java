package com.devdmin.core.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerificationLinksSender {
    @Autowired
    private JavaMailSender mailSender;

    public final String SUBJECT = "Potwierdzenie rejestracji";
    public final String TEXT = "Zweryfikuj swoje konto klikając w link: http://localhost:8080/token/";


    public void send(UUID token, String recipientAddress){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(SUBJECT);
        email.setText(TEXT + token.toString());

        mailSender.send(email);
    }
}