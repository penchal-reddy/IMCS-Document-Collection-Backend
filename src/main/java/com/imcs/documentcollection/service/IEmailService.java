package com.imcs.documentcollection.service;

import com.imcs.documentcollection.dto.EmailAttachment;

import java.util.Set;

public interface IEmailService {
    boolean sendEmail(String to, String cc, String bcc, String subject, String content, String contentType, Set<EmailAttachment> attachments);
    boolean sendEmail(String to, String cc, String bcc, String subject, String content, String contentType);
}
