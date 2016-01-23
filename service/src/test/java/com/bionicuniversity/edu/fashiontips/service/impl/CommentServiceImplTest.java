package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotAllowedActionException;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link CommentServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentService commentService = new CommentServiceImpl();

    @Mock
    private CommentDao commentDao;

    @Mock
    private PostDao postDao;

    @Test
    public void testSave_shouldReturnSavedComment() throws Exception {

        Long postId = 234L;
        User user = new User();
        Post post = new Post(user, "some title", "some message", Post.Category.POST);
        post.setId(postId);

        Comment comment = new Comment("some text", post, user);
        Comment savedComment = new Comment(1L, comment.getText(), post, user, LocalDateTime.now());

        when(postDao.exists(postId)).thenReturn(true);
        when(postDao.getReference(postId)).thenReturn(post);
        when(commentDao.save(comment)).thenReturn(savedComment);

        assertEquals("Comments should be equal.", savedComment, commentService.save(comment, postId));

        InOrder inOrderPostDao = inOrder(postDao);
        inOrderPostDao.verify(postDao).exists(postId);
        inOrderPostDao.verify(postDao).getReference(postId);
        verify(commentDao, atMost(1)).getReference(postId);
    }

    @Test(expected = NotFoundException.class)
    public void testSave_whenPassNonexistentPostId_shouldThrowException() throws Exception {

        long postId = -1L;
        when(postDao.exists(postId)).thenReturn(false);
        commentService.save(new Comment(), postId);
        fail("Should throw an exception, when the id of non existent post was passed.");
    }

    @Test(expected = NotAllowedActionException.class)
    public void testSave_whenPostsIsAllowedCommentsFalse_shouldThrowAnException() throws Exception {

        long postId = 523L;
        Post post = new Post();
        post.setId(postId);
        post.setCommentsAllowed(false);
        Comment comment = new Comment();

        when(postDao.exists(postId)).thenReturn(true);
        when(postDao.getReference(postId)).thenReturn(post);
        when(commentDao.save(comment)).thenReturn(comment);

        commentService.save(comment, postId);
        fail("Should throw an exception if comments are not allowed.");
    }

    @Test(expected = NullPointerException.class)
    public void testFindAllByPostId_whenPassNull_shouldThrowException() throws Exception {

        commentService.findAllByPostId(null);
        fail("The exception was not thrown when the null post id was sent.");
    }

    @Test
    public void testFindAllByPostId_whenPassExistentPostId_shouldReturnListOfComments() throws Exception {

        Long postId = 78L;
        User user = new User();
        Post post = new Post(user, "some title", "some message", Post.Category.POST);
        post.setId(postId);

        List<Comment> comments = asList(
                new Comment("some text", post, user),
                new Comment("some another text", post, user)
        );

        when(postDao.getReference(postId)).thenReturn(post);
        when(commentDao.findAllByPost(post)).thenReturn(comments);

        assertEquals("Should return the same list of comments.", comments, commentService.findAllByPostId(postId));
    }

    @Test(expected = NullPointerException.class)
    public void testBlock_whenPostIsNull_shouldThrowAnException() throws Exception {

        commentService.block(null);
        fail("Should throw an exception, when post is null.");
    }

    @Test
    public void testBlock_whenPostsCommentsAreAllowed_shouldReturnFalse() throws Exception {

        Post post = new Post();
        post.setCommentsAllowed(true);
        when(postDao.save(post)).thenReturn(post);
        boolean result = commentService.block(post);
        assertFalse("Should block comments and return false - comments are no allowed.", result);
    }

    @Test
    public void testBlock_whenPostsCommentsBlocked_shouldReturnTrue() throws Exception {

        Post post = new Post();
        post.setCommentsAllowed(false);
        when(postDao.save(post)).thenReturn(post);
        boolean result = commentService.block(post);
        assertTrue("Should block comments and return false - comments are no allowed.", result);
    }
    
    @Test(expected = AccessDeniedException.class)
    public void testHideById_whenPassInappropriateLoginAgainstCommentsOwner_shouldThrowException() throws Exception {
        User user = new User();
        user.setLogin("login1");
        Comment comment = new Comment(1L, "cool", null, user);
        when(commentDao.getById(anyLong())).thenReturn(comment);
        commentService.hideById(1L, "login4444");
    }

    @Test(expected = NotFoundException.class)
    public void testHideById_whenPassNotExistentCommentId_shouldThrowException() throws Exception {
        when(commentDao.getById(anyLong())).thenReturn(null);
        commentService.hideById(anyLong(), "no matter");
    }

    @Test
    public void testHideById_whenArgsAreValid_shouldCallMethodSave() throws Exception {
        User user = new User();
        user.setLogin("login");
        Comment comment = new Comment(1L, "some texr", null, user, LocalDateTime.now());
        when(commentDao.getById(anyLong())).thenReturn(comment);
        commentService.hideById(1L, "login");
        verify(commentDao).save(comment);
    }
}