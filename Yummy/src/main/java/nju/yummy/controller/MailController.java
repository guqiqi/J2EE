package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import nju.yummy.serviceImpl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private JavaMailSender mailSender;//spring 提供的邮件发送类

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String sendMail(@RequestBody JSONObject jsonParam) {
        System.out.println(jsonParam.toJSONString());
        String email = jsonParam.getString("email");

        System.out.println(email);
        boolean isSuccess = new MailServiceImpl().sendEmail(email, mailSender);

        JSONObject result = new JSONObject();

        result.put("isSuccess", isSuccess);

        return result.toJSONString();
    }

}
