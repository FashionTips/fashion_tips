package com.bionic.edu.crud;

import com.bionic.edu.entity.Usr;

/**
 * Created by maxim on 11/5/15.
 */
public interface UserService {
    Usr add(Usr user);
    void delete(long id);
    Usr get(long id);
    void update(Usr user);
}
