package nju.yummy.controller;

import nju.yummy.serviceImpl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private JavaMailSender mailSender;//spring 提供的邮件发送类

    @RequestMapping("/send")
    public void sendMail(){
        new MailServiceImpl().sendEmail("161250031@smail.nju.edu.cn", mailSender);
    }

}
