package com.bionicuniversity.edu.fashiontips.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Controller which handles authenticate requests
 */
@CrossOrigin
@RestController
public class AuthController {

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<?> login(HttpSession session) {
        return ResponseEntity.ok().header("Token", session.getId()).build();
    }
}
