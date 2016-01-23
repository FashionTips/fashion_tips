package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Dao interface.
 *
 * @author Volodymyr Portianko
 * @author Maksym Dolia
 * @since 20/11/2015
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Returns user by given login.
     *
     * @param login login
     * @return found user
     */
    User getByLogin(String login);

    /**
     * Looks for and returns user with given email.
     *
     * @param email email
     * @return {@code User}, if exists, {@code null} otherwise.
     */
    @Transactional
    User findByEmail(String email);
}
