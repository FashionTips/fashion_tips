package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import org.junit.Ignore;
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
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.*;

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
        assertReflectionEquals(NEW_POST_AFTER_SAVE, testPost, IGNORE_DEFAULTS);

        List<Post> testList = postDao.findAll();
        /*Check that the new Post added*/
        assertReflectionEquals(LIST_WITH_NEW_POST, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testUpdatePost() {
        Post testPost = postDao.getById(1L);
        testPost.setTitle("UPDATE title");
        testPost.setTextMessage("UPDATE what fits me with these pants?");
        postDao.save(testPost);
        testPost = postDao.getById(1L);

        assertReflectionEquals(UPDATE_POST1, testPost, IGNORE_DEFAULTS);

        List<Post> testList = postDao.findAll();
        assertReflectionEquals(LIST_IF_UPDATE_FIRST_POST, testList, IGNORE_DEFAULTS, LENIENT_ORDER, LENIENT_DATES);
    }

    @Test
    public void testDeletePost() {
        /*Delete Post(ID = 1) from DB*/
        postDao.delete(1L);
        List<Post> testList = postDao.findAll();

        assertReflectionEquals(LIST_IF_DELETE_FIRST_POST, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testGetPostById() throws IOException, ClassNotFoundException {
        Post testPost = postDao.getById(1L);
        assertReflectionEquals(POST1, testPost, IGNORE_DEFAULTS);
    }

    @Test
    public void testGetAll() {
        List<Post> testList = postDao.getAll();
        assertReflectionEquals(LIST_OF_POSTS, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testFindByUser() {
        List<Post> testFindByUser3 = postDao.findByUser(USER3);
        assertReflectionEquals(FIND_BY_USER3_SORTED_BY_CREATED, testFindByUser3, IGNORE_DEFAULTS);
    }

    @Test
    public void testFindByWord() {
        List<Post> testFindByWordAgain = postDao.findByWord("Again");
        assertReflectionEquals(FIND_BY_WORD_AGAIN_SORTED_BY_CREATED, testFindByWordAgain, IGNORE_DEFAULTS);
    }

    @Test
    public void testFindByCategory() {
        List<Post> testFindByCategoryPost = postDao.findByCategory(Post.Category.POST);
        List<Post> testFindByCategoryQuestion = postDao.findByCategory(Post.Category.QUESTION);
        assertReflectionEquals(FIND_BY_CATEGORY_POST, testFindByCategoryPost, IGNORE_DEFAULTS);
        assertReflectionEquals(FIND_BY_CATEGORY_QUESTION, testFindByCategoryQuestion, IGNORE_DEFAULTS);
    }

    @Test
    public void testFindAll() {
        List<Post> testFindAll = postDao.findAll();
        assertReflectionEquals(FIND_ALL_SORTED_BY_CREATED, testFindAll, IGNORE_DEFAULTS);
    }

    @Test
    @Ignore
    public void testAddNotValidPost() {
        thrown.expect(ConstraintViolationException.class);
        postDao.save(NOT_VALID_POST);
        fail("Should not save not valid entities.");
    }

    @Test
    public void testFindWithHiddenPost() {
       // postDao.save(POST_MATCHER.deepClone(HIDDEN_POST));

        List<Post> testFindByUser3 = postDao.findByUser(USER3);
        assertReflectionEquals(FIND_BY_USER3_SORTED_BY_CREATED, testFindByUser3, IGNORE_DEFAULTS);

        List<Post> testFindByWordAgain = postDao.findByWord("Again");
        assertReflectionEquals(FIND_BY_WORD_AGAIN_SORTED_BY_CREATED, testFindByWordAgain, IGNORE_DEFAULTS);

        List<Post> testFindByCategoryPost = postDao.findByCategory(Post.Category.POST);
        assertReflectionEquals(FIND_BY_CATEGORY_POST, testFindByCategoryPost, IGNORE_DEFAULTS);

        List<Post> testFindAll = postDao.findAll();
        assertReflectionEquals(FIND_ALL_SORTED_BY_CREATED, testFindAll, IGNORE_DEFAULTS);
    }

    @Test
    public void testFindMine() {
        postDao.save(POST_MATCHER.deepClone(HIDDEN_POST));

        List<Post> testFindMine = postDao.findMine(USER3);
        assertReflectionEquals(FIND_BY_USER3_WITH_HIDDEN_POSTS, testFindMine, IGNORE_DEFAULTS);
    }

    @Test
    public void testFindUnpublished() {
        List<Post> testFindMine = postDao.findUnpublished();
        assertReflectionEquals(Arrays.asList(SCHEDULED_POST), testFindMine, IGNORE_DEFAULTS);
    }
}
