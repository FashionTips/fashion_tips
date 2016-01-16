package com.bionicuniversity.edu.fashiontips.web.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Web controller to handle requests to basic app pages.
 *
 * @author Maksym Dolia
 * @since 17.12.2015.
 */
@Controller
public class BaseController {

    /**
     * Handles requests to login page.
     *
     * @return view name
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return isLoggedIn() ? "redirect:/" : "login";
    }

    /**
     * Handles requests to register page.
     *
     * @return view name
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return isLoggedIn() ? "redirect:/" : "register";
    }

    /* check whether the user is logged in */
    private boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (!(auth instanceof AnonymousAuthenticationToken));
    }
}
