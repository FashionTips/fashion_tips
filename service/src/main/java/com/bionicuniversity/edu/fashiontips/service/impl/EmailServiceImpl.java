package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Alexandr Laktionov
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Inject
    private MailSender mailSender;

    @Value("${application.mail.apiurl}")
    private String apiUrl;

    @Value("${application.mail.weburl}")
    private String webUrl;

    @Value("${application.mail.subject}")
    private String subject;

    @Value("${application.mail.from}")
    private String fromAddress;

    @Value("${application.mail.refstart}")
    private String refStart;

    @Value("${application.mail.refend}")
    private String refEnd;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void sentEmail(String email, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        StringBuilder sb = new StringBuilder(webUrl).append("?token=").append(message);
        mailMessage.setText(sb.toString());
        mailSender.send(mailMessage);
    }
}
