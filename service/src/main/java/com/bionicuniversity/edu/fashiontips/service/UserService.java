package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.User;


/**
 * User Service Interface.
 */
public interface UserService extends GenericService<User, Long> {

    User getByLogin(String login);
}
