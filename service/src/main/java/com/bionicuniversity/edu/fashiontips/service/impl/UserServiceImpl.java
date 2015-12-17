package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.RoleDao;
import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.Role;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.Collections;

/**
 * User Service Implementation.
 *
 * @author Volodymyr Portianko
 * @author Maksym Dolia
 * @since 20/11/2015
 */
@Named
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

    @Inject
    private RoleDao roleDao;

    @Override
    @Transactional
    public User save(User user) {
        user.setId(null);   // workaround, because due to id is common
                            // for all entities, and it is impossible
                            // to manage serialization only for User class id;

        encodePassword(user);
        Role role = roleDao.find("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        return super.save(user);
    }

    @Override
    public void update(User user) {
        encodePassword(user);
        super.update(user);
    }

    @Override
    public User findOne(String login) {
        try {
            return ((UserDao) repository).getByLogin(login);
        } catch (NoResultException ex) {
            throw new NotFoundException(String.format("User with login %s is not found.", login));
        }
    }

    @Override
    public boolean check(String login) {
        try {
            ((UserDao) repository).getByLogin(login);
            return false;
        } catch (NoResultException ex) {
            return true;
        }
    }

    private void encodePassword(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
    }
}
