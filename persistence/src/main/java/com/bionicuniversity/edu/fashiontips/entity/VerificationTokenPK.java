package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.annotation.Create;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class for the composite primary key for VerificationToken
 * consist from email and type of verification token
 * Created by Sergiy on 2/11/2016.
 */
@Embeddable
public class VerificationTokenPK implements Serializable {


    @NotBlank(message = "Email could not be empty.", groups = {Create.class})
    @Email(message = "The given string is not email.", groups = {Create.class})
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, insertable = true)
    private Type type;

    /**
     * Type of verification token.
     * It is used to create verification tokens for different goals
     * EMAIL_VERIFICATION use only for email verification etc.
     * Created by Sergiy on 2/11/2016.
     */
    public enum Type{
        EMAIL_VERIFICATION, PASSWORD_RESET
    }

    public VerificationTokenPK() {
    }

    public VerificationTokenPK(String email, Type type) {
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationTokenPK that = (VerificationTokenPK) o;
        return Objects.equals(email, that.email) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, type);
    }
}