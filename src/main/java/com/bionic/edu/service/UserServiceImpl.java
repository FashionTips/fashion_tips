package com.bionic.edu.service;

import com.bionic.edu.LoggerWrapper;
import com.bionic.edu.model.User;
import com.bionic.edu.repository.UserRepository;
import com.bionic.edu.util.exception.NotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
@Named
public class UserServiceImpl implements UserService {

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServiceImpl.class);

    @Inject
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void update(User user) throws NotFoundException {
        if (userRepository.save(user) == null)
            throw LOG.getNotFoundException("Could not update user with id=" + user.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        if (!userRepository.delete(id)) throw LOG.getNotFoundException("Could not delete user with id=" + id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        User user = userRepository.get(id);
        if (user == null) throw LOG.getNotFoundException("Could not found user with id=" + id);
        return user;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) throw LOG.getNotFoundException("Could not found user with email=" + email);
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
