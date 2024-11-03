package com.personal_project.voting_system;

import com.personal_project.voting_system.dtos.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean User user(){
        return new User();
    }
}
