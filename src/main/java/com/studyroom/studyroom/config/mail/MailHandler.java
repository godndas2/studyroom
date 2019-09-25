package com.studyroom.studyroom.config.mail;

import com.studyroom.studyroom.model.mail.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailHandler {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage(final Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());

        javaMailSender.send(message);
    }

//    private MimeMessage mimeMessage;
//    private MimeMessageHelper mimeMessageHelper;
//
//    public MailHandler(JavaMailSender javaMailSender) throws MessagingException {
//        this.javaMailSender = javaMailSender;
//        mimeMessage = javaMailSender.createMimeMessage();
//        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//    }
//
//    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
//        mimeMessageHelper.setFrom(email, name);
//    }
//
//    public void setTo(String email) throws MessagingException {
//        mimeMessageHelper.setTo(email);
//    }
//
//    public void setSubject(String subject) throws MessagingException {
//        mimeMessageHelper.setSubject(subject);
//    }
//
//    public void setText(String text) throws MessagingException {
//        mimeMessageHelper.setText(text, true);
//    }
//
//    // 첨부파일 https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch26s03.html
//    public void addInline(String contentId, Resource resource) throws
//            MessagingException {
//        mimeMessageHelper.addInline(contentId, resource);
//    }
//
//    public void addInline(String contentId, File file) throws
//            MessagingException {
//        mimeMessageHelper.addInline(contentId, file);
//    }
//
//    public void addInline(String contentId, DataSource source) throws
//            MessagingException {
//        mimeMessageHelper.addInline(contentId, source);
//    }
//
//    public void send() {
//        try {
//            javaMailSender.send(mimeMessage);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
}
