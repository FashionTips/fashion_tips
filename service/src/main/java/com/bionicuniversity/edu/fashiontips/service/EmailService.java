package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.OutboxEmail;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

/**
 * @author Alexandr Laktionov
 */
public interface EmailService {

    void sentVerificationToken(String email, String message);
    void createNotificationAboutNewComment(Post post, User commentAuthor);
    void sendAllOutboxEmail();
    boolean sendOutboxEmail(OutboxEmail outboxEmail);

}
