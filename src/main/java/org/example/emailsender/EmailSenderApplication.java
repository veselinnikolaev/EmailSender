package org.example.emailsender;

import org.example.emailsender.service.EmailSenderService;
import org.example.emailsender.utils.HttpRequestUtil;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return _ -> {  };
    }
}