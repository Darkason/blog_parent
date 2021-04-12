package com.darkason.email.service.impl;

import com.darkason.email.entity.EmailEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailServiceImpl {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(EmailEntity emailEntity) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        BeanUtils.copyProperties(emailEntity, mailMessage);
        mailMessage.setFrom(emailFrom);
        javaMailSender.send(mailMessage);
    }
}
