package com.personal_project.voting_system.services;

import com.personal_project.voting_system.email.MailManager;
import com.personal_project.voting_system.security.TokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ServiceEmail {


    private final String PORT_APP;

    private final MailManager mailManager;
    private final TokenData tokenData;

    @Autowired
    public ServiceEmail( @Value("${server.port}") String portApp, MailManager mailManager, TokenData tokenData) {
        PORT_APP = portApp;
        this.mailManager = mailManager;
        this.tokenData = tokenData;
    }

    public void sendEmail(String email, String messageEmail){
        mailManager.sendMailSimple(email,"Confirmación del correo electronico",messageEmail);
    }

    public void sendEmailCheck(String email){
        Map<String,String> claims = new LinkedHashMap<>();
        claims.put("email",email);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("title","Confirma tu correo electronico");

        map.put("url",String.format("http://127.0.0.1:%s/api/validation/email/%s",PORT_APP,tokenData.generateTokenEmail(claims)));
        mailManager.sendMail(email,
                "Confirmación de correo electronico","todo bien todo correcto","ValidEmail",map);
    }
}
