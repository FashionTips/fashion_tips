package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.OutboxEmailDao;
import com.bionicuniversity.edu.fashiontips.entity.OutboxEmail;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import com.bionicuniversity.edu.fashiontips.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Alexandr Laktionov
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Inject
    private MailSender mailSender;

    @Inject
    private OutboxEmailDao outboxEmailDao;

    @Value("${application.mail.web.url}")
    private String webUrl;

    @Value("${application.mail.web.RegistraionPath}")
    private String registraionPath;

    @Value("${application.mail.web.ResetPasswordPath}")
    private String resetPasswordPath;

    @Value("${application.mail.subject}")
    private String subject;

    @Value("${application.mail.from}")
    private String fromAddress;

    @Value("${application.mail.refstart}")
    private String refStart;

    @Value("${application.mail.refend}")
    private String refEnd;

    private static final String SIGNATURE = "\n\nThanks for using FashionTips!\nThe FashionTips Team";

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void sentVerificationToken(String email, VerificationTokenPK.Type type, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(email);
        switch (type) {
            case EMAIL_VERIFICATION:
                mailMessage.setSubject(subject);
                mailMessage.setText(webUrl + registraionPath + "?token=" + message + SIGNATURE);
                break;
            case PASSWORD_RESET:
                mailMessage.setSubject("Here's the link to reset your password");
                mailMessage.setText(
                        "To reset your FashionTips password, click on the link: "
                                + webUrl + resetPasswordPath + "?token=" + message
                                + "\n\nThe link will expire in 24 hours, so be sure to use it right away."
                                + SIGNATURE);
                break;
            default:
                throw new IllegalArgumentException("Invalid type data");
        }


        mailSender.send(mailMessage);
    }

    @Override
    @Transactional
    public void createNotificationAboutNewComment(Post post, User commentAuthor) {
        if (post.isNotificationEnabled() && !post.getUser().equals(commentAuthor)) {
            OutboxEmail outboxEmail = new OutboxEmail();
            outboxEmail.setFrom(fromAddress);
            outboxEmail.setTo(post.getUser().getEmail());
            outboxEmail.setSubject(commentAuthor.getLogin() + " commented on your post " + post.getTitle());
            outboxEmail.setText("To see the comment use the link: " + webUrl + "/post/" + post.getId() + SIGNATURE);
            outboxEmailDao.save(outboxEmail);
        }
    }

    @Transactional
    public void sendAllOutboxEmail() {
        List<OutboxEmail> outboxEmails = outboxEmailDao.getAll();
        if (!outboxEmails.isEmpty()) {
            outboxEmails.stream()
                    .filter(this::sendOutboxEmail)
                    .forEach(outboxEmail -> outboxEmailDao.delete(outboxEmail));
        }
    }

    @Transactional
    public boolean sendOutboxEmail(OutboxEmail outboxEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(outboxEmail.getFrom());
        message.setTo(outboxEmail.getTo());
        message.setSubject(outboxEmail.getSubject());
        message.setText(outboxEmail.getText());
        try {
            mailSender.send(message);
        } catch (MailException e) {
            return false;
        }
        return true;
    }
}
