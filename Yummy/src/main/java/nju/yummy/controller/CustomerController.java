package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import nju.yummy.serviceImpl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CustomerController {
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestBody JSONObject jsonParam) {
        System.out.println(jsonParam.toJSONString());
        String userId = jsonParam.getString("email");
        String password = jsonParam.getString("password");

        System.out.println(userId);
        System.out.println(password);

        JSONObject result = new JSONObject();

        result.put("type", "");
        result.put("permission", "");
        result.put("isValid", "yes");

        return result.toJSONString();


//        new MailServiceImpl().sendEmail("161250031@smail.nju.edu.cn", mailSender);
    }

}
