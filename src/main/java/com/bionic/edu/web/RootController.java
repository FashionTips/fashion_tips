package com.bionic.edu.web;

import com.bionic.edu.LoggedUser;
import com.bionic.edu.model.User;
import com.bionic.edu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by VPortianko on 12.11.2015.
 */
@Controller
public class RootController {

    @Inject
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:requests";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap,  @RequestParam(value = "message", required = false) String message) {
        modelMap.put("message", message);
        return "login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(User user, BindingResult result, SessionStatus status, ModelMap model) {
        status.setComplete();
        userService.update(LoggedUser.safeGet().updateUser(user));
        return "redirect:requests";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("register", true);
        return "profile";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(User user, SessionStatus status, ModelMap model) {
        userService.save(user);
        status.setComplete();
        return "redirect:login?message=registered";
    }

}
