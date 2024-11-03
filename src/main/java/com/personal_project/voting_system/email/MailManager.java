package com.personal_project.voting_system.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailManager {

    @Value("${spring.mail.username}")
    private String from;

    JavaMailSender javaMailSender;

    @Autowired
    public MailManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email, String messageEmail){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setSubject("Confirmación de correo");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(messageEmail);
            mimeMessageHelper.setFrom(from);
            javaMailSender.send(message);
        }
        catch (MessagingException exception){
            exception.getCause();
        }
    }
}
