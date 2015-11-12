package com.bionic.edu;

import com.bionic.edu.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by VPortianko on 11.11.2015.
 */
public class LoggedUser implements UserDetails, Serializable {

    private final User user;

    public LoggedUser(User user) {
        this.user = user;
    }

    public static int getLoggedUserId() {
        return 100000;
    }

    public static int getLoggedId() {
        return safeGet().getUser().getId();
    }

    public User getUser() {
        return user;
    }

    public User updateUser(User updatedUser) {
        this.user.setEmail(updatedUser.getEmail());
        this.user.setName(updatedUser.getName());
        this.user.setPassword(updatedUser.getPassword());
        return this.user;
    }

    public static LoggedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object user = auth.getPrincipal();
        return (user instanceof LoggedUser) ? (LoggedUser) user : null;
    }

    public static LoggedUser get() {
        LoggedUser user = safeGet();
        Objects.requireNonNull(user, "No authorized user found");
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> "ROLE_USER");
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
