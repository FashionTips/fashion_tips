package com.bionic.edu.web;

import com.bionic.edu.LoggedUser;
import com.bionic.edu.model.Comment;
import com.bionic.edu.service.CommentService;
import com.bionic.edu.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * Created by VPortianko on 11.11.2015.
 */
@Controller
public class CommentController {

    @Inject
    private CommentService commentService;

    @Inject
    private RequestService requestService;

    @RequestMapping(value = "requests/{id}/comments", method = RequestMethod.POST)
    public String save(Comment comment, SessionStatus status, @PathVariable("id") int id) {
        comment.setDateTime(LocalDateTime.now());
        comment.setRequest(requestService.get(id));
        commentService.save(comment, LoggedUser.getLoggedUserId());
        status.setComplete();
        return "redirect:/requests/" + id;
    }
}
