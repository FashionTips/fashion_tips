package com.bionic.edu.crud;

import com.bionic.edu.entity.Usr;

/**
 * Interface for UserDao.
 * Created by maxim on 11/5/15.
 */
public interface UserDao {

    Usr create(Usr user);
    Usr read(long id);
    void update(Usr user);
    void delete(long id);
}
