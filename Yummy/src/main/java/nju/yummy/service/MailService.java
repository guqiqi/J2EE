package nju.yummy.service;

public interface MailService {
    /**
     * 发送邮件
     * @param email 邮箱
     * @return 是否成功
     */
    public boolean sendEmail(String email);
}
