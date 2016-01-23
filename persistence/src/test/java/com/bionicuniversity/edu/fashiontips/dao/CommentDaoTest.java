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
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;
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
        assertReflectionEquals(NEW_COMMENT_AFTER_SAVE, testComment, IGNORE_DEFAULTS);
        List<Comment> testList = commentDao.getAll();
        assertReflectionEquals(LIST_WITH_NEW_COMMENT, testList,IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testDeleteComment() {
        commentDao.delete(1L);
        List<Comment> testList = commentDao.getAll();
        assertReflectionEquals(LIST_IF_DELETE_FIRST_COMMENT, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testGetCommentById() {
        Comment testComment = commentDao.getById(1L);
        assertReflectionEquals(COMMENT1, testComment, IGNORE_DEFAULTS);
    }

    @Test
    public void testGetAll() {
        List<Comment> testList = commentDao.getAll();
        assertReflectionEquals(LIST_OF_COMMENTS, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }


    @Test
    public void testUpdateComment() {
        Comment testComment = commentDao.getById(3L);
        testComment.setText("UpdateFromTest");
        commentDao.save(testComment);
        Comment updatedComment = commentDao.getById(3L);

        assertReflectionEquals(UPDATE_COMMENT3, updatedComment, IGNORE_DEFAULTS);

        List<Comment> testList = commentDao.getAll();
        assertReflectionEquals(LIST_IF_UPDATE_COMMENT3, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testGetCommentByPost() {
        List<Comment> testList = commentDao.findAllByPost(POST1);
        assertReflectionEquals(Arrays.asList(COMMENT1, COMMENT2_HIDDEN), testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }
}

