package nju.yummy.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailService {
    /**
     * 发送邮件
     *
     * @param email 邮箱
     * @param mailSender java mail object
     * @return 是否成功
     */
    public boolean sendEmail(String email, JavaMailSender mailSender);
}
