package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.annotation.Create;
import com.bionicuniversity.edu.fashiontips.annotation.UniqueEmail;
import com.bionicuniversity.edu.fashiontips.annotation.Update;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Alexandr Laktionov
 */
@Entity
@Table(value = "verification_token", uniqueConstraints = {@UniqueConstraint(name = "email", columnNames = "email")})
public class VerificationToken {

    @Id
    @NotBlank(message = "Email could not be empty.", groups = {Create.class})
    @Email(message = "The given string is not email.", groups = {Create.class, Update.class})
    @UniqueEmail(groups = Create.class)
    private String email;

    @JsonIgnore
    private String token;

    @JsonIgnore
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "expaired_time")
    private LocalDateTime expairedTime;

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
