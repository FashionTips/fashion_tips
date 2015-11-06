package com.bionic.edu.repository;

import com.bionic.edu.model.User;

import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
public interface UserRepository {

    User save(User user);

//    false if not found
    boolean delete(int id);

//    null if not found
    User get(int id);

//    null if not found
    User getByEmail(String email);

    List<User> getAll();
}
