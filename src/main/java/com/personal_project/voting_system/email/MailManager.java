package com.personal_project.voting_system.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;

@Component
public class MailManager {

    @Value("${spring.mail.username}")
    private String from;

    private  JavaMailSender javaMailSender;
    private  TemplateEngine templateEngine;

    @Autowired
    public MailManager(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }




    public void sendMailSimple(String email, String Subject, String messageEmail){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setSubject(Subject);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    true,"UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(messageEmail);
            mimeMessageHelper.setFrom(from);


            javaMailSender.send(message);
        }
        catch (MessagingException exception){
            exception.getCause();
        }
    }


    public void sendMail(String email, String Subject, String messageEmail,
                               String template , Map<String,Object> body){
        MimeMessage message = javaMailSender.createMimeMessage();


        try {

            message.setSubject(Subject);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    true,"UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(messageEmail);
            mimeMessageHelper.setFrom(from);


            Context context = new Context();
            context.setVariables(body);
            String contentHTML = templateEngine.process("ValidEmail", context);
            mimeMessageHelper.setText(contentHTML, true);

            javaMailSender.send(message);
        }
        catch (MessagingException exception){
            exception.getCause();
        }
    }
}
