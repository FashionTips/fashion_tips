package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.annotation.Create;
import com.bionicuniversity.edu.fashiontips.annotation.UniqueEmail;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Alexandr Laktionov
 */
@Entity
@Table(name = "verification_token", uniqueConstraints =
        {@UniqueConstraint(name = "verification_token_email", columnNames = "email")})
public class VerificationToken implements Serializable {

    /**
     * Avoid duplicate invoking generate a new token
     */
    public static final long EXPAIRED_PERIOD = 60L;

    @Id
    @JsonProperty(value = "email")
    @NotBlank(message = "Email could not be empty.", groups = {Create.class})
    @Email(message = "The given string is not email.", groups = {Create.class})
    @UniqueEmail(groups = Create.class)
    private String email;

    @JsonIgnore
    @JsonProperty(value = "token")
    private String token;

    @JsonIgnore
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "expaired_time")
    private LocalDateTime expairedTime;

    @JsonIgnore
    @Column(name = "verified")
    private boolean verified;

    public VerificationToken() {
        this.expairedTime = LocalDateTime.now().plusSeconds(EXPAIRED_PERIOD);
        this.verified = false;
    }

    public VerificationToken(String email) {
        this();
        this.email = email;
    }

    public VerificationToken(String email, String token) {
        this();
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationToken)) return false;

        VerificationToken that = (VerificationToken) o;

        return email.equals(that.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "token='" + token + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
