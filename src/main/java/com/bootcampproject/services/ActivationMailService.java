package com.bootcampproject.services;

import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivationMailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    public ActivationMailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(User user) throws MailException
    {
        SimpleMailMessage mail = new SimpleMailMessage();
        String link = "http://localhost:8080/activate/"+user.getUuid();
        log.info("Your link is "+ link);
        mail.setTo(user.getEmail());
        mail.setFrom("mohdkashif1108@gmail.com");
        mail.setSubject("This is to unlock your account");
        mail.setText(link);
        log.info("in service sending mail");
        javaMailSender.send(mail);
    }

}
