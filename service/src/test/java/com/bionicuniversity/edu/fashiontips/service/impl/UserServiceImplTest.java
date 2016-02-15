package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.RoleDao;
import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.Role;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link UserServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Spy
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private VerificationTokenDao verificationTokenDao;

    @Test(expected = NullPointerException.class)
    public void testSave_whenUserNull_shouldThrowException() throws Exception {
        userService.save(null, null);
        fail("Should throw exception when null was passed.");
    }

    @Test
    public void testSave_whenUserNotNull_shouldReturnSavedUser() throws Exception {

        Role role = new Role(1L, "USER");
        String password = "somePassword";
        User user = new User(1L, "login", "some@email", "somePassword", Collections.singletonList(role));
        VerificationToken verificationToken = new VerificationToken("some@email", VerificationTokenPK.Type.EMAIL_VERIFICATION, "12345");

        when(roleDao.find("ROLE_USER")).thenReturn(role);
        when(userDao.save(user)).thenReturn(user);

        assertEquals("The users should match", user, userService.save(user, verificationToken));
        assertNotEquals("Password should be hashed", password, user.getPassword());
        verify(roleDao, atMost(1)).find("ROLE_USER");
        verify(userDao, atMost(1)).save(user);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdate_whenUserIsNull_shouldThrowException() throws Exception {
        userService.update(null);
        fail("Should throw exception when null was passed.");
    }

    @Test
    public void testUpdate_whenUserIsNotNull_shouldSaveUserToDAO() throws Exception {
        User user = new User();
        userService.update(user);
        verify(userDao, atMost(1)).save(user);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateUserUserData_whenUserIsNull_shouldThrowAnException() throws Exception {
        userService.update(null, new User());
        fail("Should throw exception when null was passed.");
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateUserUserData_whenUserDataIsNull_shouldThrowAnException() throws Exception {
        userService.update(new User(), null);
        fail("Should throw exception when null was passed.");
    }

    @Test
    public void testUpdateUserUserData_whenArgsAreValid_shouldCallMethodUpdate() throws Exception {

        String password = "somePassword";
        User user = new User(1L, "someLogin", "some@email", password, Collections.singletonList(new Role("ROLE_USER")));

        User userData = new User(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
        );

        String aboutMe = "Some about me text";
        LocalDate date = LocalDate.now();
        userData.setAboutMe(aboutMe);
        userData.setBirthday(date);

        doNothing().when(userService).update(user);

        userService.update(user, userData);

        assertEquals("The user should be updated with given user data.", userData.getAboutMe(), user.getAboutMe());
        assertEquals("The user should be updated with given user data.", userData.getBirthday(), user.getBirthday());
        assertNotEquals("Password should be hashed", password, user.getPassword());
        verify(userService, atMost(1)).update(user);
    }

    @Test
    public void testFindOne_shouldCallTheDaoToGetTheUser() throws Exception {
        String login = "login";
        userService.findOne(login);
        verify(userDao, atMost(1)).getByLogin(login);
    }

    @Test
    public void testFindOneId_shouldCallTheDaoToGetTheUser() throws Exception {
        long id = 1L;
        userService.findOne(id);
        verify(userDao, atMost(1)).getById(id);
    }

    @Test
    public void testCheckLogin_whenLoginIsTaken_shouldReturnFalse() throws Exception {
        String login = "someLogin";
        when(userDao.getByLogin(login)).thenReturn(new User());
        assertFalse("The login is already taken, should return false.", userService.checkLogin(login));
        verify(userDao, atMost(1)).getByLogin(login);
    }

    @Test
    public void testCheckLogin_whenLoginIsAvailable_shouldReturnTrue() throws Exception {
        String login = "someLogin";
        when(userDao.getByLogin(login)).thenReturn(null);
        assertTrue("The login is available, should return true.", userService.checkLogin(login));
        verify(userDao, atMost(1)).getByLogin(login);
    }

    @Test
    public void testCheckEmail_whenEmailIsTaken_shouldReturnFalse() throws Exception {
        String email = "some@email";
        when(userDao.findByEmail(email)).thenReturn(new User());
        assertFalse("The email is taken, should return false.", userService.checkEmail(email));
        verify(userDao, atMost(1)).findByEmail(email);
    }

    @Test
    public void testCheckEmail_whenEmailIsAvailable_shouldReturnTrue() throws Exception {
        String email = "some@email";
        when(userDao.findByEmail(email)).thenReturn(null);
        assertTrue("The email is available, should return true.", userService.checkEmail(email));
        verify(userDao, atMost(1)).findByEmail(email);
    }
}