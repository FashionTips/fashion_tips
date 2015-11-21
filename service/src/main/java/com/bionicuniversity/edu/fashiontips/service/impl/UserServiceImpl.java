package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;

import javax.inject.Named;

/**
 * User Service Implementation.
 */
@Named
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {
}
