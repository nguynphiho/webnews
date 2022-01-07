package com.webnewsproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendMailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String email, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("testmailnet612@gmail.com", "Báo Ông Thắng");
        helper.setTo(email);
        String subject = "Here's the link to reset your password";
        String content = "<p> Hello </p>" +
                "<p> You have a request to reset your password </p>" +
                "<p>Click the link below to change your password </p>" +
                "<p><b><a href=\""+link+"\">Click here</a></b></p>" +
                "<p>Ignore this email if you do remember your password or you have not made request</p>" +
                "<p>Thank you!</p>";
        helper.setSubject(subject);
        helper.setText(content,true);

        mailSender.send(message);
    }
}
