package org.dog.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Author: Odin
 * @Date: 2023/1/6 22:38
 * @Description:
 */


@Component
@Slf4j
public class EmailUtils {
  @Autowired
  JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  String username;

  public void sendHtml(String title, String html, String to) {
    MimeMessage mailMessage = javaMailSender.createMimeMessage();
    //需要借助Helper类
    MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
    try {
      helper.setFrom(username);  // 必填
      helper.setTo(to);   // 必填
//            helper.setBcc("密送人");   // 选填
      helper.setSubject(title);  // 必填
      helper.setSentDate(new Date());//发送时间
      helper.setText(html, true);   // 必填  第一个参数要发送的内容，第二个参数是不是Html格式。
      javaMailSender.send(mailMessage);
    } catch (MessagingException e) {
      log.error("发送邮件失败", e);
    }
  }

}
