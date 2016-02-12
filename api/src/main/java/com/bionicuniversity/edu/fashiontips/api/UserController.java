package com.bionicuniversity.edu.fashiontips.api;


import com.bionicuniversity.edu.fashiontips.annotation.Create;
import com.bionicuniversity.edu.fashiontips.annotation.Update;
import com.bionicuniversity.edu.fashiontips.api.util.ImageUtil;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.VerificationTokenService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotAllowedActionException;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Controller for users.
 *
 * @author Maksym Dolia
 * @author Sergiy Borysenko
 * @since 19/11/2015
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Inject
    private VerificationTokenService verificationTokenService;

    /**
     * Returns user with given id, status OK.
     *
     * @param id user's id
     * @return user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity getUser(@PathVariable long id) {
        User user = userService.findOne(id)
                .orElseThrow(() -> new NotFoundException(String.format("The user with id %d was not found.", id)));
        if (user.getAvatar() != null) {
            ImageUtil.createUrlName(user.getAvatar());
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Returns user by given params.
     *
     * @param login user's login
     * @return user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/by")
    public ResponseEntity getUserByLogin(@RequestParam String login) {
        User user = userService.findOne(login)
                .orElseThrow(() -> new NotFoundException(String.format("The user with login %s was not found.", login)));
        ;
        if (user.getAvatar() != null) {
            ImageUtil.createUrlName(user.getAvatar());
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Creates new user with given data.
     *
     * @param user data
     * @return created user
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@Validated(Create.class) @RequestBody User user,
                                     @RequestParam String token) {
        VerificationToken verificationToken = verificationTokenService.getByToken(token)
                .orElseThrow(() -> new NotFoundException("This token does not exist"));
        if (verificationToken.isVerified()) return new ResponseEntity(HttpStatus.FORBIDDEN); //TODO accept it???
        verificationToken.setVerified(true);
        User savedUser = userService.save(user, verificationToken);
        verificationTokenService.update(verificationToken);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("users/" + savedUser.getId())
                .build()
                .toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    /**
     * Updates user with given data.
     *
     * @param userData user data
     * @param id       user's id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@Validated(Update.class) @RequestBody User userData, @PathVariable long id) {
        User user = userService.findOne(id)
                .orElseThrow(() -> new NotFoundException(String.format("The user with id %d was not found.", id)));
        userService.update(user, userData);
    }

    /**
     * Check whether the given login is available for registration.
     *
     * @param login login to check
     * @return {@code true} if there is not user with given login, {@code false} otherwise
     */
    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public boolean available(@RequestParam(value = "login", required = false) String login,
                             @RequestParam(value = "email", required = false) String email) {

        if (login != null && email == null) {
            return userService.checkLogin(login);
        }

        if (login == null && email != null) {
            return userService.checkEmail(email);
        }

        // at this step email is always != null
        return login != null && userService.checkLogin(login) && userService.checkEmail(email);
    }

    @RequestMapping(value = "/tokens/create", method = RequestMethod.POST)
    public ResponseEntity signUp(@Validated(Create.class) @RequestBody VerificationToken token) {
        Optional<VerificationToken> verifiedToken = null;
        if (!(verifiedToken = verificationTokenService.getToken(token)).isPresent()) {
            verificationTokenService.registrateNewToken(token);
            return ResponseEntity.ok().build();
        }
        VerificationToken verificationToken = verifiedToken.get();
        if (verificationToken.getExpairedTime() != null
                && LocalDateTime.now().isBefore(verificationToken.getExpairedTime()))
            throw new NotAllowedActionException("Verification email was already sent. Resending is possible after 1 minute.");

        verificationTokenService.resentToken(verificationToken);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/tokens/check", method = RequestMethod.POST)
    public ResponseEntity getEmailByToken(@RequestBody VerificationToken token) {

        if (token.getToken() == null) throw new NotFoundException("Token does not present");

        VerificationToken verificationToken =
                verificationTokenService.getByToken(token.getToken()).
                        orElseThrow(() -> new NotFoundException("Your verification code is invalid."));

        if (verificationToken.isVerified())
            throw new IllegalArgumentException("Your verification code is already used.");
        if (verificationToken.getType() == VerificationTokenPK.Type.PASSWORD_RESET
                && ChronoUnit.HOURS.between(verificationToken.getExpairedTime(), LocalDateTime.now()) > 24L) {
            throw new IllegalArgumentException("Your verification code is expired");
        }

        return new ResponseEntity(verificationToken, HttpStatus.OK);
    }

    /**
     * Reset password
     *
     * @param userData user data with new password
     * @param token    verification token which was send to user email
     */
    @RequestMapping(value = "/reset/password", method = RequestMethod.PUT)
    public ResponseEntity resetPassword(@RequestBody User userData, @RequestParam String token) {
        VerificationToken verificationToken = verificationTokenService.getByToken(token)
                .orElseThrow(() -> new NotFoundException("This token does not exist"));

        if (verificationToken.getType() != VerificationTokenPK.Type.PASSWORD_RESET || verificationToken.isVerified()) {
            throw new IllegalArgumentException("Your verification code is invalid.");
        }

        User user = userService.findByEmail(verificationToken.getEmail()).get();
        userService.update(user, userData);
        verificationToken.setVerified(true);
        verificationTokenService.update(verificationToken);
        return ResponseEntity.ok().build();
    }

}
