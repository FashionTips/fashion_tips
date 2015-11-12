package com.bionic.edu.web;

import com.bionic.edu.model.Comment;
import com.bionic.edu.model.Request;
import com.bionic.edu.service.CommentService;
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

    @Inject
    private CommentService commentService;

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable("id") int id, Model model) {
        Request request = requestService.get(id);
        model.addAttribute("request", request);
        model.addAttribute("comments", commentService.getAllByRequest(id));
        model.addAttribute("newComment", new Comment());
        return "request";
    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestService.getAll());
        return "requests";
    }
}
