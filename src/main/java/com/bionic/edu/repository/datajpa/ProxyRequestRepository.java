package com.bionic.edu.repository.datajpa;

import com.bionic.edu.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by VPortianko on 09.11.2015.
 */
@Transactional(readOnly = true)
public interface ProxyRequestRepository extends JpaRepository<Request, Integer> {

    @Override
    @Transactional
    Request save(Request request);

    @Modifying
    @Transactional
    @Query("DELETE FROM Request r WHERE r.id=:id AND r.user.id=:userId")
    int delete(@Param("id")int id, @Param("userId")int userId);

    @Query("SELECT r FROM Request r WHERE r.id=:id AND r.user.id=:userId")
    Request get(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT r FROM Request r WHERE r.user.id=:userId ORDER BY r.dateTime DESC ")
    List<Request> getAll(@Param("userId")int userId);
}
