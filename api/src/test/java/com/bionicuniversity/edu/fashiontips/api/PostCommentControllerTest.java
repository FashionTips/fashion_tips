package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
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
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    @Inject
    private CommentDao commentDao;

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private FilterChainProxy springSecurityFilterChain;

    /* media type to check, that server response in json, encoding is UTF-8 */
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Comment comment1;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();

        comment1 = commentDao.getById(1L);
    }

    @Test
    public void testSave() throws Exception {
        Comment testComment = new Comment("Test", comment1.getPost(), comment1.getUser());

        mockMvc.perform(post(COMMENTS_API_URL)
                .with(user("login1"))
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
    public void testGetAll() throws Exception {
        mockMvc.perform(get(COMMENTS_API_URL).with(user("Login1")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(comment1.getId().intValue())))
                .andExpect(jsonPath("$[0].text", is(comment1.getText())))
                .andExpect(jsonPath("$[0].created", is(
                        comment1.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))))
                )
                .andExpect(jsonPath("$[0].author.login", is(comment1.getUser().getLogin())));
    }

    /**
     * Convert Comment to JSON.
     *
     * @param comment comment to be converted
     * @return JSON as string
     * @throws IOException
     */
    protected String json(Comment comment) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(comment);
    }
}