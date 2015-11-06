package com.bionic.edu.repository.datajpa;

import com.bionic.edu.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
@Transactional(readOnly = true)
public interface ProxyUserRepository extends JpaRepository<User, Integer> {

    @Override
    @Transactional
    User save(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id")int id);

    @Override
    User findOne(Integer id);

    User getByEmail(String email);

    @Override
    List<User> findAll(Sort sort);
}
