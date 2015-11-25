package com.bionicuniversity.edu.fashiontips.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test cases to test {@code UsersController} class.
 *
 * @author Maksym Dolia
 * @since 24.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml"
})
@ActiveProfiles("dev")
public class UsersControllerTest {

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    /* media type to check, that server response in json, encoding is UTF-8 */
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testProcessUserNotFoundEx() throws Exception {
        mockMvc.perform(get("/users/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType));
    }
}