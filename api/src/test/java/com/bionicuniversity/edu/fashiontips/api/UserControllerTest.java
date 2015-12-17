package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test cases to test {@code UserController} class.
 *
 * @author Maksym Dolia
 * @author Vadym Golub
 * @since 24/11/2015
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
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("dev")
public class UserControllerTest {

    private static final String USERS_URL = "/users";

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private FilterChainProxy springSecurityFilterChain;

    @Inject
    private UserService userService;

    private MockMvc mockMvc;

    /* user for testing */
    private User user;

    /* media type to check, that server response in json, encoding is UTF-8 */
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
        user = userService.get(1L);
    }

    @Test
    public void testGetUserAuthorised() throws Exception {

        mockMvc.perform(get(USERS_URL + "/" + user.getId())
                .with(user(user.getLogin()))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.login", is(user.getLogin())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void testGetUserUnauthorised() throws Exception {

        mockMvc.perform(get(USERS_URL + "/" + user.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testProcessUserNotFoundExWithAuth() throws Exception {

        mockMvc.perform(get(USERS_URL + "/-1")
                .with(user(user.getLogin()))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testCreateNewUser() throws Exception {

        User user = new User("testLogin", "some@email.com", "testPassword");
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);  // to disable password ignore
        String userJson = mapper.writeValueAsString(user);

        mockMvc.perform(post(USERS_URL)
                .content(userJson)
                .contentType(contentType)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.login").value(is(user.getLogin())))
                .andExpect(jsonPath("$.email").value(is(user.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void testUpdateUserWithUserAuthorised() throws Exception {

        User user = new User(this.user.getLogin(), "another@email.com", "testPassword");
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);  // to disable password ignore
        String userJson = mapper.writeValueAsString(user);

        mockMvc.perform(put(USERS_URL + "/" + this.user.getId())
                .contentType(contentType)
                .content(userJson)
                .with(user(this.user.getLogin()))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginAvailableWithNonexistentLogin() throws Exception {

        String login = "someTestLogin";

        mockMvc.perform(get(USERS_URL + "/available")
                .param("login", login)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(Boolean.TRUE.toString()));
    }

    @Test
    public void testLoginAvailableWithExistentLogin() throws Exception {

        String login = user.getLogin();

        mockMvc.perform(get(USERS_URL + "/available")
                .param("login", login)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(Boolean.FALSE.toString()));
    }
}