package cn.edu.cqupt.scie.tths.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Encoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by why on 2017/4/2.
 */
public class MailUtil {

    public static void sendMail(String username,String email,String URL) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mime, true, "utf-8");
            helper.setFrom("wang.haoyu233@foxmail.com");
            helper.setTo(email);
            helper.setSubject("密码找回");
            helper.setText(username+"老师，您好，请在30分钟内点击此链接继续找回密码:"+URL);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(587);
        mailSender.setUsername("wang.haoyu233@foxmail.com");
        mailSender.setPassword("xgojzwyvoumccaac");//授权码

//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(email);
//        mail.setFrom("wang.haoyu233@foxmail.com");
//        BASE64Encoder enc = new BASE64Encoder();//该类位于jre/lib/rt.jar中
//        //解决linux下中文乱码问题
//        mail.setSubject("=?UTF-8?B?"+enc.encode("密码找回".getBytes())+"?=");
//
//        mail.setText(username+"老师，您好，请点击此链接继续找回密码:"+URL);
        mailSender.send(mime);
        System.out.println("success");
    }
}
