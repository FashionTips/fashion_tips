package com.bionicuniversity.edu.fashiontips.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *  Custom Logout filter class for Spring Security. Allows to handle OPTIONS request in CORS during logout process
 */
public class CustomLogoutFilter extends LogoutFilter {
    public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (((HttpServletRequest) req).getMethod().equals("OPTIONS")) {
            chain.doFilter(req,res);
            return;
        }
        super.doFilter(req, res, chain);
    }
}
