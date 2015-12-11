package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
/**
 * Class for testing UserService
 * @author Sergiy
 */
@ActiveProfiles("dev")
@ContextConfiguration(locations = {
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml"
})
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Inject
    private UserService userService;

    @Test
    public void testUpdate() throws Exception {
        /*Make some changes in User(ID = 1)*/
        User testUser = userService.get(1L);
        testUser.setEmail("new email");

        /*Update User(ID = 1) in DB*/
        userService.update(testUser);
        testUser = userService.get(1L);
        assertEquals("new email", testUser.getEmail());
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InUpdate_IfIdDoesNotExist() throws NotFoundException {
        /*Update User which not exist in DB*/
        userService.update(new User(25L, "test", "test", "test"));
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InDelete_IfIdDoesNotExist() throws NotFoundException {
        /*Delete User which not exist in DB*/
        userService.delete(-4L);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InGet_IfIdDoesNotExist() throws NotFoundException {
        /*Get User which not exist in DB*/
        userService.get(-4L);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InGetByLogin_IfLoginDoesNotExist() throws NotFoundException {
        /*Get User which not exist in DB*/
        userService.getByLogin("tttttt");
    }
}
