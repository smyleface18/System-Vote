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

import java.util.Map;

@Component
public class MailManager {

    @Value("${spring.mail.username}")
    private String from;

    JavaMailSender javaMailSender;
    TemplateEngine templateEngine;

    public MailManager(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
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
                               String template , Map<String,Object> varible){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setSubject(Subject);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    true,"UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(messageEmail);
            mimeMessageHelper.setFrom(from);

            Context context = new Context();
            context.setVariables(varible);
            String contentHTML = templateEngine.process(template,context);

            javaMailSender.send(message);
        }
        catch (MessagingException exception){
            exception.getCause();
        }
    }
}
