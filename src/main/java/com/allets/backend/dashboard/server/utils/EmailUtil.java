package com.allets.backend.dashboard.server.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Component
public class EmailUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private JavaMailSenderImpl mailSender1;

    @Autowired
    private JavaMailSenderImpl mailSender2;

    @Autowired
    private JavaMailSenderImpl mailSender3;

    @Autowired
    private JavaMailSenderImpl mailSender4;

    @Autowired
    private JavaMailSenderImpl mailSender5;

    @Autowired
    private JavaMailSenderImpl mailSender6;

    private List<Map.Entry<Integer, JavaMailSenderImpl>> mailSenderList = new ArrayList<Map.Entry<Integer, JavaMailSenderImpl>>();

    private Integer emailSwitch;

    private String emailForTest;

    private String emailCC;

    @PostConstruct
    public void initMailSenderMap() {

        mailSenderList.add(new AbstractMap.SimpleEntry<Integer, JavaMailSenderImpl>(1, mailSender1));
        mailSenderList.add(new AbstractMap.SimpleEntry<Integer, JavaMailSenderImpl>(2, mailSender2));
        mailSenderList.add(new AbstractMap.SimpleEntry<Integer, JavaMailSenderImpl>(3, mailSender3));
        mailSenderList.add(new AbstractMap.SimpleEntry<Integer, JavaMailSenderImpl>(4, mailSender4));
        mailSenderList.add(new AbstractMap.SimpleEntry<Integer, JavaMailSenderImpl>(5, mailSender5));
        mailSenderList.add(new AbstractMap.SimpleEntry<Integer, JavaMailSenderImpl>(6, mailSender6));

    }

    public void send(final String from, final String to, final String subject, final String contentHtml) throws MessagingException {

        Collections.shuffle(mailSenderList);
        for (Map.Entry<Integer, JavaMailSenderImpl> entry : mailSenderList) {
            try {
                JavaMailSenderImpl mailSender = entry.getValue();
                MimeMessage message = mailSender.createMimeMessage();
                if (entry.getKey() == 1) {
                    mailSender.setUsername("noreply@allets.com");
                } else {
                    mailSender.setUsername("noreply" + entry.getKey() + "@allets.com");
                }
                message.addHeader("Precedence", "bulk");
//                Multipart mp = new MimeMultipart("related");
//                BodyPart bodyPart = new MimeBodyPart();
//                bodyPart.setDataHandler(new DataHandler(contentHtml,"text/html;charset=UTF-8"));
//                message.setContent(mp);
                MimeMessageHelper helper = new MimeMessageHelper(message, 2, "UTF-8");
                if (emailSwitch == 1) {
                    helper.setTo(to);
                    helper.setSubject(subject);
                } else if (emailSwitch == 0) {
                    if(to.equals("2669432@naver.com")||to.equals("420317056@qq.com"))
                    {
                        helper.setTo(to);
                        helper.setSubject(subject);
                    }else {
                        helper.setTo(emailForTest);
                        helper.setSubject(subject + " [ This email is sent by the dev/stg server, only for the dev/stg environment, prod environment won't have these words. If you are not QA,please ignore it.]");
                    }
                }
                if (entry.getKey() == 1) {
                    helper.setFrom("noreply@allets.com");
                } else {
                    helper.setFrom("noreply" + entry.getKey() + "@allets.com");
                }

                if (StringUtils.isNotBlank(emailCC)) {
                    InternetAddress[] iaToListcs = new InternetAddress().parse(emailCC);
                    helper.setCc(iaToListcs);
                }
                helper.setText(contentHtml, true);
                mailSender.send(message);
                break;
            } catch (Exception e) {
                LOG.warn("Failure to send email by noreply" + entry.getKey() + "@allets.com, ", e);
            }
        }
    }

    public Integer getEmailSwitch() {
        return emailSwitch;
    }

    public void setEmailSwitch(Integer emailSwitch) {
        this.emailSwitch = emailSwitch;
    }

    public String getEmailForTest() {
        return emailForTest;
    }

    public void setEmailForTest(String emailForTest) {
        this.emailForTest = emailForTest;
    }

    public String getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }
}
