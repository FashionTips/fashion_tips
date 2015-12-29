package com.bionicuniversity.edu.fashiontips.web.controllers;

import com.bionicuniversity.edu.fashiontips.web.config.MvcConfig;
import com.bionicuniversity.edu.fashiontips.web.config.WebSecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
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
@TestExecutionListeners(listeners = {WithSecurityContextTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class})
public class BaseControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilter(filterChainProxy)
                .defaultRequest(get("/").with(testSecurityContext()))
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
        mockMvc.perform(get("/register"))
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser
    public void testRegisterForLoggedInUser() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(redirectedUrl("/"));
    }
}