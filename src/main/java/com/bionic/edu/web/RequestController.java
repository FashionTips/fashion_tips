package com.bionic.edu.web;

import com.bionic.edu.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by VPortianko on 10.11.2015.
 */
@Controller
public class RequestController {

    @Inject
    private RequestService requestService;

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable("id") int id, Model model) {
        model.addAttribute("request", requestService.get(id));
        return "request";
    }
}
