package com.bionicuniversity.edu.fashiontips.api;


import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Controller for users.
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws NotFoundException {
        User user = userService.get(id);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by")
    public ResponseEntity<User> getUserByLogin(@RequestParam(value = "login")String login) {
        User user = userService.getByLogin(login);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
