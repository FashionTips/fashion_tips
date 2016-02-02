package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Country;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.bionicuniversity.edu.fashiontips.util.TestUtil.json;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
@ActiveProfiles("dev")
public class UserControllerTest {

    private static final String USERS_URL = "/users";
    private static final String TEST_USER_LOGIN = "login1";
    private static final String TEST_USER_PASSWORD = "1111";
    private static final String CHECK_TOKEN_URL = "/tokens/check";

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private UserService userService;

    private MockMvc mockMvc;

    /* user for testing */
    private User user;

    /* media type to check, that server response in json, encoding is UTF-8 */
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private String token = "bddb893798745da191393b0bfcfe454967857d84c2ad0d420dc4f9cf74086510";
    private String badToken = "bddb893798745da191393b0bfcfe454967857d84c2ad0d420dc4f90000000000";
    private String verifiedToken = "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000002222";
    private String email = "some@email.com";

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        user = userService.findOne(1L).get();
    }

    @Test
    public void testGetUser() throws Exception {

        mockMvc.perform(get(USERS_URL + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.login", is(user.getLogin())))
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.created").value(
                        user.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                ))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.hideAge").value(user.isHideAge()))
                .andExpect(jsonPath("$.gender").value(user.getGender()))
                .andExpect(jsonPath("$.location").value(user.getLocation()));

    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    public void testProcessUserNotFoundExWithAuth() throws Exception {

        mockMvc.perform(get(USERS_URL + "/-1"))
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
                .param("token", token)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.login").value(is(user.getLogin())))
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    @Transactional
    public void testUpdateUserWithUserAuthorised() throws Exception {

        User user = new User(this.user.getLogin(), "another@email.com", "testPassword");
        user.setAboutMe("Some text about me");
        user.setBirthday(LocalDate.of(2000, 5, 23));
        user.setBlogUrl(new URL("https://www.google.com"));
        Country country = new Country();
        country.setId(1);
        user.setCountry(country);
        user.setFirstName("John");
        user.setLastName("Gold");
        user.setGender(User.Gender.GUY);
        user.setHideAge(true);
        user.setInstagram("@my_instagram");
        user.setLocation("Los Angeles, LA");
        user.setOccupation("Unknown");
        user.setWebsiteUrl(new URL("https://www.google.com"));

        mockMvc.perform(put(USERS_URL + "/" + this.user.getId())
                .contentType(contentType)
                .content(json(user))
        )
                .andExpect(status().isOk());
    }

    @Ignore("Should be debugged up.")
    @Test
    @WithMockUser("someAnotherUser")
    public void testUpdateUserByAnotherUser() throws Exception {

        mockMvc.perform(put(USERS_URL + "/1")
                .content(json(user))
                .contentType(contentType)
        )
                .andExpect(status().isForbidden());
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

        mockMvc.perform(get(USERS_URL + "/available")
                .param("login", TEST_USER_LOGIN)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(Boolean.FALSE.toString()));
    }

    @Test
    public void checkTokenTestIfExists() throws Exception {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpairedTime(null);

        mockMvc.perform(post(USERS_URL + CHECK_TOKEN_URL)
                .content(json(verificationToken))
                .contentType(contentType)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    public void checkTokenTestIfNotExists() throws Exception {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(badToken);
        verificationToken.setExpairedTime(null);

        mockMvc.perform(post(USERS_URL + CHECK_TOKEN_URL)
                .content(json(verificationToken))
                .contentType(contentType)
        )
                .andExpect(status().isNotFound());

    }

    @Test
    public void checkTokenTestIfExistsAndVerified() throws Exception {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(verifiedToken);
        verificationToken.setExpairedTime(null);

        mockMvc.perform(post(USERS_URL + CHECK_TOKEN_URL)
                .content(json(verificationToken))
                .contentType(contentType)
        )
                .andExpect(status().isForbidden());

    }
}