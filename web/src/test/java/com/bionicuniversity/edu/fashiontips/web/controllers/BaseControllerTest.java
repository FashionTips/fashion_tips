package com.bionicuniversity.edu.fashiontips.web.controllers;

import com.bionicuniversity.edu.fashiontips.web.config.MvcConfig;
import com.bionicuniversity.edu.fashiontips.web.config.WebSecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Test cases for {@code BaseController} class.
 *
 * @author Maksym Dolia
 * @since 17.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MvcConfig.class, WebSecurityConfig.class})
public class BaseControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testLoginForAnonymousUser() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser
    public void testLoginForLoggedInUser() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testRegisterForAnonymousUser() throws Exception {
        mockMvc.perform(get("/emailVerification"))
                .andExpect(view().name("emailVerification"));
    }

    @Test
    @WithMockUser
    public void testRegisterForLoggedInUser() throws Exception {
        mockMvc.perform(get("/emailVerification"))
                .andExpect(redirectedUrl("/"));
    }
}