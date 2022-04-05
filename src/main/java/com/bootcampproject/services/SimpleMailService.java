package com.bootcampproject.services;


import com.bootcampproject.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

    public void sendMail(User user)
    {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("mohdkashif1108@gmail.com");
        mail.setSubject("This is to inform that your account has been activated");
        mail.setText("Your account has been activated and now you can enjoy our service freely");
        javaMailSender.send(mail);
    }
}
