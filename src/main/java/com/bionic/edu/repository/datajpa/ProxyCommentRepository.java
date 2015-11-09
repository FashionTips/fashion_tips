package com.bionic.edu.repository.datajpa;

import com.bionic.edu.model.Comment;
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
public interface ProxyCommentRepository extends JpaRepository<Comment, Integer> {

    @Override
    @Transactional
    Comment save(Comment comment);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.id=:id AND c.user.id=:userId")
    int delete(@Param("id")int id, @Param("userId")int userId);

    @Query("SELECT c FROM Comment c WHERE c.id=:id")
    Comment get(@Param("id") int id);

    @Query("SELECT c FROM Comment c WHERE c.request.id=:requestId ORDER BY c.dateTime DESC ")
    List<Comment> getAllByRequest(@Param("requestId")int requestId);
}
