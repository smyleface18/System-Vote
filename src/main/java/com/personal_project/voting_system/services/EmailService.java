package com.personal_project.voting_system.services;

import com.personal_project.voting_system.email.MailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailManager mailManager;

    @Autowired
    public EmailService(MailManager mailManager) {
        this.mailManager = mailManager;
    }

    public void sendEmail(String email, String messageEmail){
        mailManager.sendMailSimple(email,"Confirmación del correo electronico",messageEmail);
    }
}
