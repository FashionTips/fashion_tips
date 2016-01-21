package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
}