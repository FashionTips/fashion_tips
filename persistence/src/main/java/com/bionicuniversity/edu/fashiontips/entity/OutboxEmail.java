package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Stores outbox messages
 * Created by Sergiy on 2/4/2016.
 */
@Entity
@Table(name = "outbox_emails")
public class OutboxEmail extends BaseEntity<Long> {

    @Column(name = "email_from")
    private String from;

    @Column(name = "email_to")
    private String to;
    private String subject;
    private String text;

    public OutboxEmail() {
    }

    public OutboxEmail(Long id, String from, String to, String subject, String text) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "OutboxEmail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                "} " ;
    }
}