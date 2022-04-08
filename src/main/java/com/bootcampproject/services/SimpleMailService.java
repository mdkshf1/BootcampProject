package com.bootcampproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class SimpleMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public SimpleMailService()
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to,String subject,String text)
    {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(to);
                    mail.setFrom("mohdkashif1108@gmail.com");
                    mail.setSubject(subject);
                    mail.setText(text);
                    javaMailSender.send(mail);
                    log.info("mail sent");
                } catch (MailException e) {
                    log.info("Error in mail sending");
                }
            }
        });
    }
}
