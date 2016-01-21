package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test cases for {@link PostServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @InjectMocks
    private PostService postService = new PostServiceImpl();

    @Mock
    private PostDao postDao;

    @Test
    public void testFindAllByUser() throws Exception {
        postService.findAllByUser(new User(), new User());
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
        postService.update(new Post());
        verify(postDao, times(1)).save(new Post());
    }

    @Test
    public void testDelete_whenPostExists_shouldDeleteFromDao() throws Exception {
        postService.delete(new Post());
        verify(postDao, times(1)).delete(new Post());
    }

    @Test(expected = NullPointerException.class)
    public void testDelete_whenPostIsNull_shouldThrowException() throws Exception {
        postService.delete(null);
        fail("Should throw an exception, when post is null");
    }

    @Test(expected = NullPointerException.class)
    public void testSave_whenPostIsNull_shouldThrowAnException() throws Exception {
        postService.save(null);
        fail("Should throw an exception, when post is null");
    }
}