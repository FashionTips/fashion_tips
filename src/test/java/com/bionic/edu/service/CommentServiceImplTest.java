package com.bionic.edu.service;

import com.bionic.edu.model.Comment;
import com.bionic.edu.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.bionic.edu.CommentTestData.*;
import static com.bionic.edu.RequestTestData.*;
import static com.bionic.edu.UserTestData.*;

/**
 * Created by VPortianko on 09.11.2015.
 */
@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class CommentServiceImplTest {

    @Inject
    private CommentService commentService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSave() throws Exception {
        Comment newComment = new Comment(null, REQ4, null, "new CommentText", LocalDateTime.now());
        Comment created = commentService.save(newComment, USER1_ID);
        newComment.setId(created.getId());
        COMM_MATCHER.assertListEquals(Arrays.asList(COMM4_1,newComment),commentService.getAllByRequest(REQ4_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Comment updatedComment = new Comment(COMM1_1);
        updatedComment.setCommentText("UpdatedText");
        commentService.update(updatedComment, USER2_ID);
        COMM_MATCHER.assertEquals(updatedComment, commentService.get(COMM1_1_ID));
    }

    @Test
    public void testUpdateWrongUserId() throws Exception {
        Comment updatedComment = new Comment(COMM1_1);
        updatedComment.setCommentText("UpdatedText");
        thrown.expect(NotFoundException.class);
        commentService.update(updatedComment, USER3_ID);
    }

    @Test
    public void testDelete() throws Exception {
        commentService.delete(COMM3_1_ID, USER3_ID);
        COMM_MATCHER.assertListEquals(Arrays.asList(COMM3_2, COMM3_3), commentService.getAllByRequest(REQ3_ID));
    }

    @Test
    public void testDeleteWrongUserId() throws Exception {
        thrown.expect(NotFoundException.class);
        commentService.delete(COMM3_1_ID, USER1_ID);
    }

    @Test
    public void testGet() throws Exception {
        COMM_MATCHER.assertEquals(COMM2_1, commentService.get(COMM2_1_ID));
    }

    @Test
    public void testGetAllByRequest() throws Exception {
        COMM_MATCHER.assertListEquals(Arrays.asList(COMM3_1,COMM3_2,COMM3_3), commentService.getAllByRequest(REQ3_ID));
    }
}