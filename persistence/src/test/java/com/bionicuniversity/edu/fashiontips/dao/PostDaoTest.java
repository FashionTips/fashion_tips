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

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.DaoTestData.*;
import static org.junit.Assert.assertEquals;
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
public class PostDaoTest {

    @Inject
    private PostDao postDao;

    @Inject
    private UserDao userDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAddValidPost() {
        Post post = new Post(null, USER1, "title4", "How my glasses fits me?", Post.Category.POST, new HashSet<>(), new HashSet<>(Arrays.asList(IMAGE4, IMAGE5)));
        postDao.save(post);
        post = postDao.getById(7L);

        assertEquals(POST7.toString(), post.toString());
    }

    @Test
    public void testAddNotValidPost() {
        thrown.expect(ConstraintViolationException.class);
        Post post = new Post(USER1, "", "", Post.Category.POST);
        postDao.save(post);
        fail("Should not save not valid entities.");
    }

    @Test
    public void testDeletePost() {
        /*Delete Post(ID = 1) from DB*/
        postDao.delete(1L);
        List<Post> testList = postDao.getAll();

        assertEquals(Arrays.asList(POST2, POST3, POST4, POST5, POST6).toString(), testList.toString());
    }

    @Test
    public void testGetPostById() {
        Post testPost = postDao.getById(1L);
        assertEquals(POST1.toString(), testPost.toString());
    }

    @Test
    public void testGetAll() throws Exception {
        /*Get list of all Posts from DB*/
        List<Post> testList = postDao.getAll();

        assertEquals(POSTS.toString(), testList.toString());
    }

    @Test
    public void testGetAllByUser() throws Exception {

        /*Get Posts(User ID =1) from DB*/
        List<Post> testList = postDao.getAllByUser(USER1);

        assertEquals(Arrays.asList(POST1, POST4).toString(), testList.toString());
    }
}
