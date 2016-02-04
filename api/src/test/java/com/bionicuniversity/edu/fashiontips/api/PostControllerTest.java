package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

import static com.bionicuniversity.edu.fashiontips.util.TestUtil.json;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test cases to test {@code PostController}
 *
 * @author Maksym Dolia
 * @since 29.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = {
                "classpath:spring/spring-service.xml",
                "classpath:spring/spring-persistence.xml",
                "classpath:spring/spring-security.xml",
                "classpath:spring/spring-api.xml"
        }),
        @ContextConfiguration(name = "child", locations = {
                "classpath:spring/spring-mvc.xml"
        })
})
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@Transactional
@ActiveProfiles("dev")
public class PostControllerTest {

    private static final String POSTS_API_URL = "/posts";
    private static final String TEST_USER_LOGIN_1 = "login1";
    private static final String TEST_USER_LOGIN_2 = "login2";
    private static final String TEST_USER_PASSWORD = "1111";

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private PostDao postDao;

    @Inject
    private UserDao userDao;

    private MockMvc mockMvc;

    /* Posts for testing */
    private Post post1;
    private Post post2;

    /* media type to check, that server response in json, encoding is UTF-8 */
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    /* user for testing with posts */
    private User user;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        user = userDao.getById(1L);

        post1 = postDao.getById(1L);
        post2 = postDao.getById(4L);
    }

    @Test
    public void testGetPostUserAuthorised() throws Exception {
        mockMvc.perform(get(POSTS_API_URL + "/" + post1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(post1.getId().intValue())))
                .andExpect(jsonPath("$.title", is(post1.getTitle())))
                .andExpect(jsonPath("$.textMessage", is(post1.getTextMessage())))
                .andExpect(jsonPath("$.category", is(post1.getCategory().name())))
                .andExpect(jsonPath("$.likes", is(post1.getLikedByUsers().size())))
                .andExpect(jsonPath("$.isLikedByAuthUser", is(nullValue())));
    }

    @Test
    public void testGetPostUserUnauthorised() throws Exception {
        mockMvc.perform(get(POSTS_API_URL + "/" + post1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(post1.getId().intValue())))
                .andExpect(jsonPath("$.title", is(post1.getTitle())))
                .andExpect(jsonPath("$.textMessage", is(post1.getTextMessage())))
                .andExpect(jsonPath("$.category", is(post1.getCategory().name())))
                .andExpect(jsonPath("$.likes", is(post1.getLikedByUsers().size())))
                .andExpect(jsonPath("$.isLikedByAuthUser", is(nullValue())));
    }

    @Ignore("Should be debugged up.")
    @Test
    public void testGetNonexistentPost() throws Exception {
        mockMvc.perform(get(POSTS_API_URL + "/-1"))
                .andExpect(status().isNotFound());
    }

    @Ignore("Issue: the order of posts is different whenever run test in single mode or all together.")
    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testGetPostsUserAuthorised() throws Exception {
        mockMvc.perform(get(POSTS_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[5].id", is(post1.getId().intValue())))
                .andExpect(jsonPath("$[5].title", is(post1.getTitle())))
                .andExpect(jsonPath("$[5].textMessage", is(post1.getTextMessage())))
                .andExpect(jsonPath("$[5].category", is(post1.getCategory().name())))
                .andExpect(jsonPath("$[5].likes", is(post1.getLikedByUsers().size())))
                .andExpect(jsonPath("$[5].isLikedByAuthUser", is(nullValue())))
                .andExpect(jsonPath("$[2].id", is(post2.getId().intValue())))
                .andExpect(jsonPath("$[2].title", is(post2.getTitle())))
                .andExpect(jsonPath("$[2].textMessage", is(post2.getTextMessage())))
                .andExpect(jsonPath("$[2].category", is(post2.getCategory().name())))
                .andExpect(jsonPath("$[2].likes", is(post2.getLikedByUsers().size())))
                .andExpect(jsonPath("$[2].isLikedByAuthUser", is(nullValue())));
    }

    @Test
    public void testGetPostsUserUnauthorised() throws Exception {
        mockMvc.perform(get(POSTS_API_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testSaveNewPostWithValidDataUserAuthorised() throws Exception {

        Post post = new Post(user, "Some title", "what fits me with these pants?", Post.Category.QUESTION);
        post.setImages(asList(new Image(1L, "name1"), new Image(2L, "name2")));

        mockMvc.perform(post(POSTS_API_URL)
                .contentType(contentType)
                .content(json(post))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testSaveNewPostWithNotValidDataUserAuthorised() throws Exception {
        Post post = new Post(user, "", "", Post.Category.QUESTION);

        mockMvc.perform(post(POSTS_API_URL)
                .contentType(contentType)
                .content(json(post))
        )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testSaveNewPostUserUnauthorised() throws Exception {
        Post post = new Post();
        post.setCategory(Post.Category.QUESTION);
        post.setCreated(LocalDateTime.now());
        post.setTextMessage("what fits me with these pants?");
        post.setTitle("Some title");
        post.setUser(user);

        mockMvc.perform(post(POSTS_API_URL).contentType(contentType).content(json(post)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testDeleteExistingPostUserAuthorised() throws Exception {
        mockMvc.perform(delete(POSTS_API_URL + "/" + post1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testDeleteNonexistentPost() throws Exception {
        mockMvc.perform(delete(POSTS_API_URL + "/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_2)
    public void testDeletePostByNotAuthor() throws Exception {
        mockMvc.perform(delete(POSTS_API_URL + "/" + post1.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testUpdateExistingPostUserAuthorised() throws Exception {
        post1.setTextMessage("Some another message");
        post1.setTitle("Some another title.");

        mockMvc.perform(put(POSTS_API_URL + "/" + post1.getId())
                .contentType(contentType).content(json(post1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testUpdateNonexistentPost() throws Exception {
        Post post = new Post(user, "Some title", "what fits me with these pants?", Post.Category.QUESTION);
        post.setCreated(LocalDateTime.now());
        post.setImages(asList(new Image()));

        mockMvc.perform(put(POSTS_API_URL + "/-1")
                .contentType(contentType).content(json(post)))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser("notTheAuthor")
    public void testUpdatePostByNotAuthor() throws Exception {
        post1.setTextMessage("Some another message");
        post1.setTitle("Some another title.");

        mockMvc.perform(put(POSTS_API_URL + "/" + post1.getId())
                .contentType(contentType).content(json(post1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testToggleLikedStatusOwnPost() throws Exception {
        mockMvc.perform(post(POSTS_API_URL + "/1/liked"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testToggleLikedStatus() throws Exception {
        mockMvc.perform(post(POSTS_API_URL + "/2/liked"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testFindPost_byExistingTag() throws Exception {
        mockMvc.perform(get(POSTS_API_URL + "?tag=karmaloop&tagType=store"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(post1.getId().intValue())));
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testFindPost_byExistingTagType() throws Exception {
        mockMvc.perform(get(POSTS_API_URL + "?tagType=brand"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(post1.getId().intValue())));
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN_1)
    public void testFindPost_byExistingClothes() throws Exception {
        mockMvc.perform(get(POSTS_API_URL + "?clothes=Dresses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(post1.getId().intValue())));
    }
}