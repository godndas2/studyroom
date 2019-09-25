package com.studyroom.studyroom;

import com.studyroom.studyroom.config.mail.MailHandler;
import com.studyroom.studyroom.model.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableJpaAuditing // CommonDateEntity Auditing 활성화
@SpringBootApplication
public class StudyroomApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(StudyroomApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(StudyroomApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Kakao와 통신
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    // Autowired 안됨.
    @Autowired
    private MailHandler mailService;

    @Override
    public void run(String... args) {
        log.info("Spring Mail - Sending Simple Email with JavaMailSender Example");
        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("docshyun@gmail.com");
        mail.setSubject("Sending Simple Email with JavaMailSender Example");
        mail.setContent("This tutorial demonstrates how to send a simple email using Spring Framework.");

        mailService.sendSimpleMessage(mail);
//        System.out.println("sending email..");
//
//        try {
//            sendEmail();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();;
//        }
//        System.out.println("Done");
    }

//    void sendEmail(ApplicationArguments applicationArguments) throws Exception {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("docshyun@gmail.com");
//        msg.setSubject("Test..!");
//        msg.setText("Hello world..!");
//        javaMailSender.send(msg);
//
//        javaMailSender.setFrom("no-reply@memorynotfound.com", "tester");
//        javaMailSender.setTo("docshyun@gmail.com");
//        javaMailSender.setSubject("Sending Simple Email with JavaMailSender Example");
//
//        javaMailSender.sendSimpleMessage(mail);
//    }


}
