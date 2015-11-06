package com.bionic.edu.repository;

import com.bionic.edu.model.User;
import com.bionic.edu.repository.datajpa.ProxyUserRepository;
import org.springframework.data.domain.Sort;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
@Named
public class UserRepositoryImpl implements  UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    @Inject
    private ProxyUserRepository proxy;

    @Override
    public User save(User user) {
        return proxy.save(user);
    }

    @Override
    public boolean delete(int id) {
        return proxy.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return proxy.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        return proxy.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return proxy.findAll(SORT_NAME_EMAIL);
    }
}
