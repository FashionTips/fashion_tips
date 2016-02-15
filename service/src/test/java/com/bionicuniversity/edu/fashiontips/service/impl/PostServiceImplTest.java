package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.*;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
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

    @Mock
    private TagDao tagDao;

    @Mock
    private TagTypeDao tagTypeDao;

    @Mock
    private ClothesDao clothesDao;

    @Test
    public void testFindAllForAuthor() throws Exception {
        postService.findAllByUser(new User(), new User());
        verify(postDao, times(1)).findForAuthor(new User());
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
    public void testGetById() throws Exception {
        postService.get(1L);
        verify(postDao, times(1)).getById(1L);
    }

    @Test
    public void testGetHiddenPostByIdForAuthor() throws Exception {
        long id = 1L;
        Post post = new Post();
        post.setId(id);
        post.setStatus(Post.Status.HIDDEN);
        post.setLikedByUsers(new HashSet<>());
        post.setComments(new ArrayList<>());
        User loggedUser = new User();
        loggedUser.setId(1L);
        post.setUser(loggedUser);
        when(postDao.getById(id)).thenReturn(post);
        postService.get(1L, loggedUser);
        verify(postDao, times(1)).getById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void testGetHiddenPostByIdIfLoggedUserIsNotAuthor() throws Exception {
        long id = 1L;
        Post post = new Post();
        post.setId(id);
        post.setStatus(Post.Status.HIDDEN);
        post.setUser(new User(1L,"login","email","pass", new ArrayList<>()));
        when(postDao.getById(id)).thenReturn(post);
        postService.get(1L, new User());
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

    @Test
    public void testFindAllByTagAndTagTypeValueWithValidArgs() throws Exception {
        Post post = new Post();
        List<Post> posts = Collections.singletonList(post);
        User loggedUser = new User();
        post.setUser(loggedUser);
        post.setLikedByUsers(Collections.emptySet());
        post.setComments(Collections.emptyList());
        TagType tagType = new TagType();
        tagType.setType("brand");
        tagType.setId(1L);

        when(tagDao.existsValue("some value")).thenReturn(true);
        when(tagTypeDao.findByType(tagType.getType())).thenReturn(tagType);
        when(postDao.findByTagValueAndTagTypeId("some value", tagType.getId())).thenReturn(posts);


        List<Post> actual = postService.findAllByTagAndTagTypeValue("some value", "brand", loggedUser);
        verify(postDao).findByTagValueAndTagTypeId("some value", tagType.getId());
        assertEquals(posts, actual);
    }

    @Test
    public void testFindAllByTagAndTagTypeValueWithNonexistentTagValue() throws Exception {
        User loggedUser = new User();

        when(tagDao.existsValue("nonexistent")).thenReturn(false);

        assertEquals(Collections.emptyList(), postService.findAllByTagAndTagTypeValue("nonexistent", "no matter", loggedUser));
    }

    @Test
    public void testFindAllByTagAndTagTypeValue_withValidTagValueAndNonexistentTagValue_shouldThrowException() throws Exception{
        User loggedUser = new User();

        when(tagDao.existsValue("valid")).thenReturn(true);
        when(tagTypeDao.findByType("nonexistent")).thenReturn(null);

        assertEquals(Collections.emptyList(), postService.findAllByTagAndTagTypeValue("valid", "nonexistent", loggedUser));
    }

    @Test
    public void testFindAllByTagTypeValueWithValidArgs() throws Exception {
        TagType tagType = new TagType();
        tagType.setType("some type");
        tagType.setId(1L);

        Post post = new Post();
        List<Post> posts = Collections.singletonList(post);
        User loggedUser = new User();
        post.setUser(loggedUser);
        post.setLikedByUsers(Collections.emptySet());
        post.setComments(Collections.emptyList());

        when(tagTypeDao.findByType("some type")).thenReturn(tagType);
        when(postDao.findByTagTypeId(tagType.getId())).thenReturn(posts);

        assertEquals(posts, postService.findAllByTagTypeValue(tagType.getType(), loggedUser));
    }

    @Test
    public void testFindAllByClothes_withValidName() throws Exception {
        Post post = new Post();
        List<Post> posts = Collections.singletonList(post);
        User loggedUser = new User();
        post.setUser(loggedUser);
        post.setLikedByUsers(Collections.emptySet());
        post.setComments(Collections.emptyList());
        Clothes clothes = new Clothes();
        clothes.setId(1L);
        clothes.setName("valid");

        when(clothesDao.findByName(clothes.getName())).thenReturn(clothes);
        when(postDao.findByClothesId(clothes.getId())).thenReturn(posts);

        assertEquals(posts, postService.findAllByClothes("valid", loggedUser));
    }

    @Test
    public void testFindAllByClothes_withNonexistentName_shouldThrowException() throws Exception {
        User loggedUser = new User();

        when(clothesDao.findByName("404")).thenReturn(null);

        assertEquals(Collections.emptyList(), postService.findAllByClothes("404", loggedUser));
    }

    @Test
    public void testGetLikedUsers_where_presents_likes() {
        User user1 = new User();
        User user2 = new User();
        List<User> gettingUsers = Arrays.asList(user1, user2);
        when(postDao.getLikedUsers(1L)).thenReturn(gettingUsers);
        assertEquals(gettingUsers, postService.getLikedUsers(1L));
        verify(postDao, times(1)).getLikedUsers(1L);
    }

}