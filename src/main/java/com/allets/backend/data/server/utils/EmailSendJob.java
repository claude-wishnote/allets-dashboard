package com.allets.backend.data.server.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.MessagingException;

/**
 * Created by claude on 2015-10-19.
 */
public class EmailSendJob implements Runnable {
    private static Log LOG = LogFactory.getLog(EmailSendJob.class);

    private EmailUtil emailUtil;

    private String from;

    private String to;

    private String subject;

    private String content;

    public EmailSendJob(EmailUtil emailUtil, String from, String to, String subject, String content) {
        this.emailUtil = emailUtil;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            emailUtil.send(from, to, subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
