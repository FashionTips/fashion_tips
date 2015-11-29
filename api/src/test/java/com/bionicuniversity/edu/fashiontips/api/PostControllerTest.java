package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.Category;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.servlet.Filter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
@ContextConfiguration(locations = {
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml",
        "classpath:spring/spring-security.xml"
})
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("dev")
public class PostControllerTest {

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private PostDao postDao;

    @Inject
    private UserDao userDao;

    @Inject
    private Filter springSecurityFilterChain;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();

        user = userDao.getById(1L);

        post1 = postDao.getById(1L);
        post2 = postDao.getById(4L);
    }

    @Test
    public void testGetPostUserAuthorised() throws Exception {
        mockMvc.perform(get("/posts/" + post1.getId()).with(httpBasic(user.getLogin(), user.getPassword())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(post1.getId().intValue())))
                .andExpect(jsonPath("$.title", is(post1.getTitle())))
                .andExpect(jsonPath("$.textMessage", is(post1.getTextMessage())))
                .andExpect(jsonPath("$.category", is(post1.getCategory().name())));
    }

    @Test
    public void testGetPostUserUnauthorised() throws Exception {
        mockMvc.perform(get("/posts/" + post1.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNonexistentPost() throws Exception {
        mockMvc.perform(get("/posts/-1").with(httpBasic(user.getLogin(), user.getPassword())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPostsUserAuthorised() throws Exception {
        mockMvc.perform(get("/posts").with(httpBasic(user.getLogin(), user.getPassword())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(post1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(post1.getTitle())))
                .andExpect(jsonPath("$[0].textMessage", is(post1.getTextMessage())))
                .andExpect(jsonPath("$[0].category", is(post1.getCategory().name())))
                .andExpect(jsonPath("$[1].id", is(post2.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(post2.getTitle())))
                .andExpect(jsonPath("$[1].textMessage", is(post2.getTextMessage())))
                .andExpect(jsonPath("$[1].category", is(post2.getCategory().name())));
    }

    @Test
    public void testGetPostsUserUnauthorised() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSaveNewPostUserAuthorised() throws Exception {
        Post post = new Post(user, "Some title", "what fits me with these pants?", Category.QUESTION);
        post.setCreated(LocalDateTime.now());

        mockMvc.perform(post("/posts").with(httpBasic(user.getLogin(), user.getPassword())).contentType(contentType).content(json(post)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    public void testSaveNewPostUserUnauthorised() throws Exception {
        Post post = new Post();
        post.setCategory(Category.QUESTION);
        post.setCreated(LocalDateTime.now());
        post.setTextMessage("what fits me with these pants?");
        post.setTitle("Some title");
        post.setUser(user);

        mockMvc.perform(post("/posts").contentType(contentType).content(json(post)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteExistingPostUserAuthorised() throws Exception {
        mockMvc.perform(delete("/posts/" + post1.getId()).with(httpBasic(user.getLogin(), user.getPassword())))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNonexistentPost() throws Exception {
        mockMvc.perform(delete("/posts/-1").with(httpBasic(user.getLogin(), user.getPassword())))
                .andExpect(status().isNotFound());
    }

    @Ignore("Need to implement check for nonexistent values. See FT12-27")
    @Test
    public void testUpdateExistingPostUserAuthorised() throws Exception {
        post1.setTextMessage("Some another message");
        post1.setTitle("Some another title.");

        mockMvc.perform(put("/posts/" + post1.getId()).with(httpBasic(user.getLogin(), user.getPassword()))
                .contentType(contentType).content(json(post1)))
                .andExpect(status().isOk());
    }

    @Ignore("Need to implement check for nonexistent values. See FT12-27")
    @Test
    public void testUpdateNonexistentPost() throws Exception {
        Post post = new Post(user, "Some title", "what fits me with these pants?", Category.QUESTION);
        post.setCreated(LocalDateTime.now());

        mockMvc.perform(put("/posts/-1").with(httpBasic(user.getLogin(), user.getPassword()))
                .contentType(contentType).content(json(post)))
                .andExpect(status().isNotFound());
    }

    /**
     * Convert Post to JSON.
     *
     * @param post post to be converted
     * @return JSON as string
     * @throws IOException
     */
    protected String json(Post post) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(post);
    }
}