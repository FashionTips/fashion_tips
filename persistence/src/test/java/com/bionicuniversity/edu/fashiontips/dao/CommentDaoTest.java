package com.bionicuniversity.edu.fashiontips.dao;


import com.bionicuniversity.edu.fashiontips.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.PostAndCommentTestData.*;

/**
 * @author alaktionov aka slav9nin
 */

@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class CommentDaoTest {

    @Inject
    private CommentDao commentDao;

    @Test
    public void testAddComment() {
        Comment testComment = commentDao.save(COMMENT_MATCHER.deepClone(NEW_COMMENT_BEFORE_SAVE));

        COMMENT_MATCHER.assertEquals(NEW_COMMENT_AFTER_SAVE, testComment);

        List<Comment> testList = commentDao.getAll();
        COMMENT_MATCHER.assertListEquals(LIST_WITH_NEW_COMMENT, testList);
    }

    @Test
    public void testDeleteComment() {
        commentDao.delete(1L);
        List<Comment> testList = commentDao.getAll();
        COMMENT_MATCHER.assertListEquals(LIST_IF_DELETE_FIRST_COMMENT, testList);
    }

    @Test
    public void testGetCommentById() {
        Comment testComment = commentDao.getById(1L);
        COMMENT_MATCHER.assertEquals(COMMENT1, testComment);
    }

    @Test
    public void testGetAll() {
        List<Comment> testList = commentDao.getAll();
        COMMENT_MATCHER.assertListEquals(LIST_OF_COMMENTS, testList);
    }


    @Test
    public void testUpdateComment() {
        Comment testComment = commentDao.getById(2L);
        testComment.setText("UpdateFromTest");
        commentDao.save(testComment);
        Comment updatedComment = commentDao.getById(2L);

        COMMENT_MATCHER.assertEquals(UPDATE_COMMENT2, updatedComment);

        List<Comment> testList = commentDao.getAll();
        COMMENT_MATCHER.assertListEquals(LIST_IF_UPDATE_COMMENT2, testList);
    }

    @Test
    public void testGetCommentByPost() {
        List<Comment> testList = commentDao.findAllByPost(POST1);
        COMMENT_MATCHER.assertListEquals(Arrays.asList(COMMENT1), testList);
    }
}
