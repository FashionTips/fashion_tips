package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.PostAndCommentTestData.*;
import static com.bionicuniversity.edu.fashiontips.UserTestData.USER3;
import static org.junit.Assert.fail;

/**
 * Class for testing PostDao
 */

@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class PostDaoTest {

    @Inject
    private PostDao postDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSavePost() {
        Post testPost = postDao.save(POST_MATCHER.deepClone(NEW_POST_BEFORE_SAVE));
        /*Check that the method Save returns correct value */
        POST_MATCHER.assertEquals(NEW_POST_AFTER_SAVE, testPost);

        List<Post> testList = postDao.getAll();
        /*Check that the new Post added*/
        POST_MATCHER.assertListEquals(LIST_WITH_NEW_POST, testList);
    }

    @Test
    public void testUpdatePost() {
        Post testPost = postDao.getById(1L);
        testPost.setTitle("UPDATE title");
        testPost.setTextMessage("UPDATE what fits me with these pants?");
        postDao.save(testPost);
        testPost = postDao.getById(1L);

        POST_MATCHER.assertEquals(UPDATE_POST1, testPost);

        List<Post> testList = postDao.getAll();
        POST_MATCHER.assertListEquals(LIST_IF_UPDATE_FIRST_POST, testList);
    }

    @Test
    public void testDeletePost() {
        /*Delete Post(ID = 1) from DB*/
        postDao.delete(1L);
        List<Post> testList = postDao.getAll();

        POST_MATCHER.assertListEquals(LIST_IF_DELETE_FIRST_POST, testList);
    }

    @Test
    public void testGetPostById() throws IOException, ClassNotFoundException {
        Post testPost = postDao.getById(1L);
        POST_MATCHER.assertEquals(POST1, testPost);
    }

    @Test
    public void testGetAll() {
        List<Post> testList = postDao.getAll();
        POST_MATCHER.assertListEquals(LIST_OF_POSTS, testList);
    }

    @Test
    public void testFindByUser() {
        List<Post> testFindByUser3 = postDao.findByUser(USER3);
        POST_MATCHER.assertListEquals(FIND_BY_USER3_SORTED_BY_CREATED, testFindByUser3);
    }

    @Test
    public void testFindByWord() {
        List<Post> testFindByWordAgain = postDao.findByWord("Again");
        POST_MATCHER.assertListEquals(FIND_BY_WORD_AGAIN_SORTED_BY_CREATED, testFindByWordAgain);
    }

    @Test
    public void testFindAll() {
        List<Post> testFindAll = postDao.findAll();
        POST_MATCHER.assertListEquals(FIND_ALL_SORTED_BY_CREATED, testFindAll);
    }

    @Test
    public void testAddNotValidPost() {
        thrown.expect(ConstraintViolationException.class);
        postDao.save(NOT_VALID_POST);
        fail("Should not save not valid entities.");
    }
}
