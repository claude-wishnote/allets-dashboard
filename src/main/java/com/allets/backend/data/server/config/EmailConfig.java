package com.allets.backend.data.server.config;

import com.allets.backend.data.server.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by claude on 2015/10/19.
 */
@Configurable
public class EmailConfig {


    @Value("${email.smtp.host}")
    private String emailSmptHost;

    @Value("${email.port}")
    private Integer emailPort;

    @Value("${email.password}")
    private String emailPassword;

    @Value("${email.switch}")
    String emailSwitch;
    @Value("${email.test}")
    String emailTest;
    @Value("${email.cc}")
    String emailCC;

    @Bean
    EmailUtil emailUtil() {
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.setEmailForTest(emailTest);
        emailUtil.setEmailSwitch(Integer.valueOf(emailSwitch));
        emailUtil.setEmailCC(emailCC);
        return emailUtil;
    }

    @Bean
    JavaMailSenderImpl javaMailSender()
    {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailSmptHost);
        javaMailSender.setPort(emailPort);
        javaMailSender.setPassword(emailPassword);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.debug", "false");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }
}
