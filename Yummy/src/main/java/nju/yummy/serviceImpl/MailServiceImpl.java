package nju.yummy.serviceImpl;

import nju.yummy.service.MailService;
import nju.yummy.util.TokenProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean sendEmail(String email, JavaMailSender mailSender) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("798838812@qq.com");
            helper.setTo(email);
            helper.setSubject("欢迎您注册yummy平台，请进行邮箱验证");
            helper.setText("<body>\n" +
                    "<div>\n" +
                    "<p style=\"margin: 10px\"><b>亲爱的用户，你好！</b></p>\n" +
                    "<p style=\"margin: 30px\"><b>欢迎您注册yummy平台，请点击下面的链接验证您的邮箱：</b></p>\n" +
                    "<a style=\"margin: 30px\" href='http:127.0.0.1:8080/#/confirm?token=" + TokenProcessor.getToken(email) +
                    "'>http:127.0.0" +
                    ".1:8080</a>\n" +
                    "</div>\n" +
                    "<body>", true);

            mailSender.send(message);
            logger.info("邮件已发送。");
            return true;

        } catch (Exception e) {
            logger.error("发送邮件时发生异常了！", e);
            return false;
        }
    }
}
