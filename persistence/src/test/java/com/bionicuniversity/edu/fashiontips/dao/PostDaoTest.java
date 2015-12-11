package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.*;
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
import java.util.*;

import static com.bionicuniversity.edu.fashiontips.ImageTestData.*;
import static org.junit.Assert.*;

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

    private static User user1 = new User("login4", "email4@example.com", "1111");
    private static Post post1 = new Post(user1, "title4", "How my glasses fits me?", Post.Category.QUESTION);
    static {
        user1.setId(4L);
    }


    @Inject
    private PostDao postDao;

    @Inject
    private UserDao userDao;

    @Inject
    private CommentDao commentDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Transactional
    public void testAddValidPost() {
        User user = new User("login4", "email4@example.com", "1111");
        user = userDao.save(user);
        System.out.println(user);
        Post post = new Post(user, "title4", "How my glasses fits me?", Post.Category.QUESTION);
        post.setTags(new HashSet<>());
        Set<Image> images = new HashSet<>();
        images.addAll(Arrays.asList(IMAGE4, IMAGE5));
        post.setImages(images);
        post = postDao.save(post);
        System.out.println(post);
        Post expected = postDao.getById(7L);
        post.setCreated(expected.getCreated());
        assertEquals(post.toString(), postDao.getById(7L).toString());
    }

    @Test
    public void testAddNotValidPost() {
        thrown.expect(ConstraintViolationException.class);
        Post post = new Post(user1, "", "", Post.Category.POST);
        postDao.save(post);
        fail("Should not save not valid entities.");
    }

    @Test
    @Transactional
    public void testDeletePost() {
        postDao.delete(1L);
        Post post = postDao.getById(1L);
        assertNull(post);
    }

    @Test
    @Transactional
    public void testGetPostById() {
        User user = userDao.getById(1L);
        Post post = new Post();
        post.setUser(user);
        post.setTitle("title1");
        post.setTextMessage("what fits me with these pants?");
        post.setCategory(Post.Category.QUESTION);
        Set<Tag> tags = new HashSet<>();
        for(Long i = 1L; i < 4; i++) {
            Tag tag = new Tag("tag" + i);
            tag.setId(i);
            tags.add(tag);
        }
        post.setTags(tags);
        Set<Image> images = new HashSet<>();
        images.addAll(Arrays.asList(IMAGE1, IMAGE2, IMAGE3));
        post.setImages(images);
        Post expected = postDao.getById(1L);
        post.setCreated(expected.getCreated());

        post.setId(1L);
        assertEquals(post.toString(), expected.toString());
    }

    @Test
    public void savePostWithCommentCascadedTest() {
        User user = userDao.getById(1L);
        List<Post> befor = postDao.getAllByUser(user);
        Post newPost = new Post(user, "title4", "How my glasses fits me?", Post.Category.QUESTION);
        Comment newComment = new Comment("Super!", newPost);
        Set<Comment> coms = new HashSet<>();
        coms.add(newComment);
        newPost.setComments(coms);
        postDao.save(newPost);
        List<Comment> commentsAfter = commentDao.getCommentsByPost(newPost);
        List<Comment> expectedComments = new ArrayList<>();
        Comment expectebleComment = new Comment("Super!", newPost);
        expectebleComment.setId(4L);
        expectedComments.add(expectebleComment);
        befor.add(newPost);
        List<Post> expected = postDao.getAllByUser(user);
        assertArrayEquals(expected.toArray(new Post[0]), befor.toArray(new Post[0]));
        assertArrayEquals(expectedComments.toArray(new Comment[0]), commentsAfter.toArray(new Comment[0]));
    }

    @Test

    public void savePostWithCommentCascadeTest() {
        Post post = postDao.getById(1L);
        Comment newComment = new Comment("Super!", post);
        newComment.setId(4L);
        Comment newComment2 = new Comment("Super2!", post);
        newComment2.setId(5L);
        Set<Comment> comments = new HashSet<>();
        comments.add(newComment);
        comments.add(newComment2);
        post.setComments(comments);
        newComment.setPost(post);
        newComment2.setPost(post);
        postDao.save(post);
        List<Comment> given = commentDao.getCommentsByPost(post);
        boolean added = given.size() > 1;
        assertTrue(added);
    }
}
