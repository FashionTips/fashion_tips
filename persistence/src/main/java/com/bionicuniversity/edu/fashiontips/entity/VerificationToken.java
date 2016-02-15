package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Alexandr Laktionov
 */
@Entity
@Table(name = "verification_token")
public class VerificationToken implements Serializable {

    /**
     * Avoid duplicate invoking generate a new token
     */
    public static final long EXPAIRED_PERIOD = 60L;

    @EmbeddedId
    @JsonIgnore
    private VerificationTokenPK id;

    @JsonProperty(value = "token")
    private String token;

    @JsonIgnore
    @Column(name = "expaired_time")
    private LocalDateTime expairedTime;

    @JsonIgnore
    @Column(name = "verified")
    private boolean verified;

    public VerificationToken() {
        this.expairedTime = LocalDateTime.now().plusSeconds(EXPAIRED_PERIOD);
        this.verified = false;
    }
    @JsonCreator
    public VerificationToken(@JsonProperty(value = "email") String email, @JsonProperty(value = "type") VerificationTokenPK.Type type) {
        this();
        this.id = new VerificationTokenPK(email, type);
    }

    public VerificationToken(String email, VerificationTokenPK.Type type, String token) {
        this();
        this.id = new VerificationTokenPK(email, type);
        this.token = token;
    }

    public String getEmail() {
        return id.getEmail();
    }

    public void setEmail(String email) {
        this.id.setEmail(email);
    }

    public VerificationTokenPK.Type getType() {
        return id.getType();
    }

    public void setType(VerificationTokenPK.Type type) {
        this.id.setType(type);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpairedTime() {
        return expairedTime;
    }

    public void setExpairedTime(LocalDateTime expairedTime) {
        this.expairedTime = expairedTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void clear() {
        this.expairedTime = null;
    }

    public VerificationTokenPK getId() {
        return id;
    }

    public void setId(VerificationTokenPK id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationToken)) return false;

        VerificationToken that = (VerificationToken) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "token='" + token + '\'' +
                ", email='" + id.getEmail() + '\'' +
                ", type='" + id.getType() + '\'' +
                '}';
    }
}
