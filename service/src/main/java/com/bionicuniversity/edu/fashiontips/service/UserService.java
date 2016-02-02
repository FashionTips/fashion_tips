package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * User Service Interface.
 *
 * @author Volodymyr Portianko
 * @author Maksym Dolia
 * @since 20/11/2015
 */
public interface UserService {

    /**
     * Looks for and returns the user with given login.
     *
     * @param login login
     * @return optional user with given login
     */
    Optional<User> findOne(String login);

    Optional<User> findOne(long id);

    /**
     * Checks whether the user with given login does not exist.
     *
     * @param login login to check
     * @return {@code true} if there is not user with given login, {@code false} otherwise
     */
    boolean checkLogin(String login);

    /**
     * Checks whether the user with given email does not exist.
     *
     * @param email email to check
     * @return {@code true} if there is not user with given email, {@code false} otherwise
     */
    boolean checkEmail(String email);

    @Transactional
    User save(User user, VerificationToken verificationToken);

    void update(User user);

    /**
     * Sets to user with given id the given user's data and saves to persistence.
     *  @param user user's id to update
     * @param userData user's data
     */
    void update(User user, User userData);
}
