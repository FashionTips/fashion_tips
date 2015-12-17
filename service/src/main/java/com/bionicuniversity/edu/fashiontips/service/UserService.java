package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.User;


/**
 * User Service Interface.
 *
 * @author Volodymyr Portianko
 * @author Maksym Dolia
 * @since 20/11/2015
 */
public interface UserService extends GenericService<User, Long> {

    /**
     * Looks for and returns the user with given login.
     *
     * @param login login
     * @return user with given login
     */
    User findOne(String login);

    /**
     * Checks whether the user with given login does not exist.
     *
     * @param login login to check
     * @return {@code true} if there is not user with given login, {@code false} otherwise
     */
    boolean check(String login);
}
