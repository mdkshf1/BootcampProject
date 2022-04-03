package com.bootcampproject.services;

import com.bootcampproject.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(User user) throws MailException
    {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("heena.dhamija@tothenew.com");
        mail.setFrom("mohd.kashif@tothenew.com");
        mail.setSubject("This is to check whether mail are sending or not");
        mail.setText("have you got the mail??");
        log.info("in service sending mail");
        javaMailSender.send(mail);
    }

}
