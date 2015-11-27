package com.bionicuniversity.edu.fashiontips.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 *  Controller which handles authenticate requests
 */
@CrossOrigin
@RestController
public class AuthController {

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login(Principal principal) {
        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void logout() {
        /* Realization of this method handled by Spring Security */
    }
}
