package com.bionicuniversity.edu.fashiontips.api;


import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws NotFoundException {
        User user = userService.get(new Long(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<Collection<User>> getAllUsers() throws UserNotFoundException{
//        List<User> users = userService.getAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//    @RequestMapping(method = RequestMethod.DELETE, value = "/:{id}")
//    public void deleteUser(@PathVariable int id) throws UserNotFoundException {
//        userService.delete(id);
//    }
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult result) throws UserUnprocessableException {
//        if (result.hasErrors()){
//            List<FieldError> errors = result.getFieldErrors();
//            StringBuilder builder = new StringBuilder();
//            for (FieldError error: errors) {
//                builder.append(error.getField() + " : " + error.getDefaultMessage() + ", ");
//            }
//            throw new UserUnprocessableException(builder.toString());
//        } else {
//            User savedUser = userService.save(user);
//            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//        }
//
//
//    }
//    @RequestMapping(method = RequestMethod.PUT, value = "/:{id}")
//    public ResponseEntity<?> putUser1(@Valid @RequestBody User user, BindingResult result, @PathVariable int id){
//        if (result.hasErrors()){
//            List<FieldError> errors = result.getFieldErrors();
//            StringBuilder builder = new StringBuilder();
//            for (FieldError error: errors) {
//                builder.append(error.getField() + " : " + error.getDefaultMessage() + ", ");
//            }
//            ErrorInformation error = new ErrorInformation("Validation errors", builder.toString());
//            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
//        } else {
//            try {
//                user.setId(id);
//                userService.update(user);
//            } catch (UserNotFoundException ex) {
//                ErrorInformation error = new ErrorInformation(ex.getClass().toString(), ex.getMessage());
//                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//    }
//
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInformation> processUserNotFoundEx(Exception e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(UserUnprocessableException.class)
//    public ResponseEntity<ErrorInformation> processUserUnprocessableEx(Exception e) {
//        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
//    }


}
