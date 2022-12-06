package com.imcs.documentcollection.service.impl;

import com.imcs.documentcollection.dto.EmailAttachment;
import com.imcs.documentcollection.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.Base64;
import java.util.Properties;
import java.util.Set;

@Service
public class GmailEmailService implements IEmailService {

    Logger logger = LoggerFactory.getLogger("GmailEmailService.class");

    private JavaMailSenderImpl mailSender;

    @Value("${notification.email.address}")
    private String username;

    @Value("${notification.email.password}")
    private String password;

    GmailEmailService() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

    @Override
    public boolean sendEmail(String to, String cc, String bcc, String subject, String content, String contentType, Set<EmailAttachment> attachments) {

            mailSender.setUsername(username);
            mailSender.setPassword(password);

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            if (cc != null && !cc.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            }
            if (bcc != null && !bcc.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
            mimeMessage.setSubject(subject);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setText(content, contentType.equals("text/html"));
            if (attachments != null) {
                for (EmailAttachment attachment : attachments) {
                    helper.addAttachment(attachment.getFileName(), new ByteArrayResource(Base64.getDecoder().decode(attachment.getBase64String())));
                }
            }
        };

        try {
            mailSender.send(preparator);
            return true;
        } catch (MailException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmail(String to, String cc, String bcc, String subject, String content, String contentType) {
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            if (cc != null && !cc.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            }
            if (bcc != null && !bcc.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content, contentType);
        };

        try {
            mailSender.send(preparator);
            return true;
        } catch (MailException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
}
