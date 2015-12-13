package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.CommentTestData;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author alaktionov aka slav9nin
 */

@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class CommentDaoTest {


    @Inject
    private PostDao postDao;

    @Inject
    private CommentDao commentDao;

    @Inject
    private UserDao userDao;

    @Test
    public void addCommentTest() {

        Comment comment = commentDao.save(CommentTestData.comment4);
        assertEquals(CommentTestData.comment4, comment);

    }

    @Test
    public void getCommentByIdTest() {
        Comment comment = commentDao.getById(1L);
        assertEquals(CommentTestData.comment1, comment);
    }

    @Test
    public void deleteCommentTest() {
        List<Comment> commentsBefore = commentDao.getAll();
        Comment comment3 = commentDao.getById(3L);
        commentDao.delete(3L);
        Comment comment = commentDao.getById(3L);
        assertNull(comment);
        List<Comment> commentsAfter = commentDao.getAll();
        assertFalse(commentsAfter.contains(comment3));
    }

    @Test
    public void updateCommentTest() {
        Comment comment = commentDao.getById(2L);
        comment.setText("UpdateFromTest");
        comment = commentDao.save(comment);
        Comment updatedComment = commentDao.getById(2L);
        assertEquals(comment,updatedComment);
    }

    @Test
    public void getCommentByPostTest() {
        List<Comment> comments = commentDao.findAllByPost(CommentTestData.post1);
        Comment[] expected = {commentDao.getById(1L)};
        assertArrayEquals(comments.toArray(new Comment[0]), expected);
    }

    @Test
    public void saveCommentTest() {
        User user3 = userDao.getByLogin("login3");
        Post post = postDao.getById(3L);
        Comment newComment = new Comment("Yeah!:)", post, user3);
        post.getComments().add(newComment);
        List<Comment> commentsBefore = commentDao.findAllByPost(post);
        commentDao.save(newComment);
        List<Comment> commentsAfter = commentDao.findAllByPost(post);
        boolean savedComment = commentDao.findAllByPost(post).contains(newComment);
        List<Comment> comments = postDao.getById(3L).getComments();
        assertTrue(savedComment);
    }
}
