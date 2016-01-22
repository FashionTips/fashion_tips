package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;

import static com.bionicuniversity.edu.fashiontips.util.TestUtil.json;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test cases for {@code PostCommentController}.
 *
 * @author Maksym Dolia
 * @since 13.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = {
                "classpath:spring/spring-service.xml",
                "classpath:spring/spring-persistence.xml",
                "classpath:spring/spring-security.xml"
        }),
        @ContextConfiguration(name = "child", locations = {
                "classpath:spring/spring-mvc.xml"
        })
})
@ActiveProfiles("dev")
@Transactional
@Rollback
public class PostCommentControllerTest {

    private static final String COMMENTS_API_URL = "/posts/1/comments";
    private static final String TEST_USER_LOGIN = "login1";
    private static final String TEST_USER_PASSWORD = "1111";

    @Inject
    private CommentDao commentDao;

    @Inject
    private WebApplicationContext webApplicationContext;

    /* media type to check, that server response in json, encoding is UTF-8 */
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Comment comment1;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        comment1 = commentDao.getById(1L);
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    public void testSave() throws Exception {
        Comment testComment = new Comment("Test", comment1.getPost(), comment1.getUser());

        mockMvc.perform(post(COMMENTS_API_URL)
                .content(json(testComment))
                .contentType(contentType)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.created", is(notNullValue())))
                .andExpect(jsonPath("$.text", is(testComment.getText())))
                .andExpect(jsonPath("$.author.login", is(testComment.getUser().getLogin())));
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    public void testGetAll() throws Exception {
        mockMvc.perform(get(COMMENTS_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(comment1.getId().intValue())))
                .andExpect(jsonPath("$[0].text", is(comment1.getText())))
                .andExpect(jsonPath("$[0].created", is(
                        comment1.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                )
                .andExpect(jsonPath("$[0].author.login", is(comment1.getUser().getLogin())));
    }
    @Test
    @WithMockUser(TEST_USER_LOGIN)
    public void testDeleteWithValidDataAndUser() throws Exception {
        mockMvc.perform(delete(COMMENTS_API_URL + "/1"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser("login2")
    public void testDeleteWithValidDataAndInappropriateUser() throws Exception {
        mockMvc.perform(delete(COMMENTS_API_URL + "/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    public void testDeleteWithNotValidCommentIdAndAppropriateUser() throws Exception {
        mockMvc.perform(delete(COMMENTS_API_URL + "/99999"))
                .andExpect(status().isNotFound());
    }
}