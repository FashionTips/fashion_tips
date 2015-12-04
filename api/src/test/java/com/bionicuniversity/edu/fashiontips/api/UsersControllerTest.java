package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.nio.charset.Charset;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test cases to test {@code UsersController} class.
 *
 * @author Vadym Golub
 * @author Maksym Dolia
 * @since 24.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml",
        "classpath:spring/spring-security.xml"
})
@ActiveProfiles("dev")
public class UsersControllerTest {

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
        mockMvc.perform(get("/users/" + user.getId()).with(httpBasic(user.getLogin(), "1111")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.login", is(user.getLogin())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(nullValue())));
    }
    @Test
    public void testGetUserUnauthorised() throws Exception {
        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void testProcessUserNotFoundExWithAuth() throws Exception {
        mockMvc.perform(get("/users/-1").with(httpBasic(user.getLogin(), "1111")))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType));
    }
}