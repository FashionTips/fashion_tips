package com.bionic.edu.web.interceptor;

import com.bionic.edu.LoggedUser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by VPortianko on 12.11.2015.
 * This interceptor adds the user to the model of every requests managed
 */
public class ModelInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty() && modelAndView.getModelMap().get("user") == null) {
            LoggedUser loggedUser = LoggedUser.safeGet();
            if (loggedUser != null) {
                modelAndView.getModelMap().addAttribute("user", loggedUser.getUser());
            }
        }
    }
}
