package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Alexandr Laktionov
 */
@Entity
@Table(name = "verification_token")
@NamedQueries({
        @NamedQuery(
                name = "VerificationToken.getByEmail",
                query = "SELECT t FROM VerificationToken t WHERE t.id.email =:email And t.id.type =:type"
        ),
        @NamedQuery(
                name = "VerificationToken.getByToken",
                query = "SELECT t FROM VerificationToken t WHERE t.token = :token"
        )
})
public class VerificationToken implements Serializable {

    /**
     * Avoid duplicate invoking generate a new token
     */
    public static final long EXPIRED_PERIOD = 60L;

    @EmbeddedId
    @JsonIgnore
    private VerificationTokenPK id;

    @JsonProperty(value = "token")
    private String token;

    @JsonIgnore
    @Column(name = "expired_time")
    private LocalDateTime expiredTime;

    @JsonIgnore
    @Column(name = "verified")
    private boolean verified;

    public VerificationToken() {
        this.expiredTime = LocalDateTime.now().plusSeconds(EXPIRED_PERIOD);
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

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void clear() {
        this.expiredTime = null;
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