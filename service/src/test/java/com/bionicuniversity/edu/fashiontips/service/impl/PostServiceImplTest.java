package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link PostServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @Spy
    @InjectMocks
    private PostService postService = new PostServiceImpl();

    @Mock
    private PostDao postDao;

    @Test
    public void testFindAllByMe() throws Exception {
        postService.findAllByUser(new User(), new User());
        verify(postDao, times(1)).findMine(new User());
    }
    @Test
    public void testFindAllByUser() throws Exception {
        postService.findAllByUser(new User(), new User(28L,"loggedUser", "email", "1111", null));
        verify(postDao, times(1)).findByUser(new User());
    }

    @Test
    public void testFindAllByCategory() throws Exception {
        postService.findAllByCategory(Post.Category.POST, new User());
        verify(postDao, times(1)).findByCategory(Post.Category.POST);
    }

    @Test
    public void testFindAll() throws Exception {
        postService.findAll(new User());
        verify(postDao, times(1)).findAll();
    }

    @Test
    public void testGetUserById() throws Exception {
        postService.get(1L);
        verify(postDao, times(1)).getById(1L);
    }

    @Test
    public void testUpdate() throws Exception {
        postService.update(new Post(), new Post());
        verify(postDao, times(1)).save(new Post());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteById_whenIdOfNonexistentPost_shouldThrowAnException() throws Exception {
        long id = 1L;
        when(postDao.exists(id)).thenReturn(false);
        postService.delete(id, new User());
        fail("Should throw an exception, when post does not exist");
    }

    @Test
    public void testDeleteById_whenIdIsCorrect_shouldCallDeleteByPost() throws Exception {
        long id = 1L;
        Post post = new Post();
        post.setId(id);
        User loggedUser = new User();
        loggedUser.setId(1L);
        post.setUser(loggedUser);
        when(postDao.exists(id)).thenReturn(true);
        when(postDao.getById(id)).thenReturn(post);
        postService.delete(id, loggedUser);
        verify(postDao).delete(id);
    }


    @Test(expected = NullPointerException.class)
    public void testSave_whenPostIsNull_shouldThrowAnException() throws Exception {
        postService.save(null);
        fail("Should throw an exception, when post is null");
    }
}