package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import com.bionicuniversity.edu.fashiontips.service.EmailService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.VerificationTokenService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Alexandr Laktionov
 */
@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    @Spy
    @InjectMocks
    private VerificationTokenService verificationTokenService = new VerificationTokenServiceImpl();

    @Mock
    private VerificationTokenDao verificationTokenDao;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Test
    public void saveTest() {
        String email = "arusich2008@ukr.net";
        String token = "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        VerificationToken verificationToken =
                new VerificationToken(email, type, token);

        when(verificationTokenDao.save(verificationToken)).thenReturn(verificationToken);

        VerificationToken gottenSaved = verificationTokenService.save(verificationToken);

        assertEquals(verificationToken, gottenSaved);

        verify(verificationTokenDao, times(1)).save(verificationToken);
    }

    @Test
    public void sendEmailRegistrationTokenTest() {
        String email = "arusich2008@ukr.net";
        String token = "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        String message = "Delivered link";
        LocalDateTime localDateTime = LocalDateTime.now();
        VerificationToken verificationToken =
                new VerificationToken(email, type, token);
        verificationToken.setExpiredTime(localDateTime);

        doNothing().when(emailService).sentVerificationToken(email, type, message);

        verificationTokenService.sendEmailRegistrationToken(verificationToken);

        verify(verificationTokenDao, times(0)).save(verificationToken);
        verify(verificationTokenDao, times(0)).update(verificationToken);
        verify(verificationTokenDao, times(0)).getToken(verificationToken);
        verify(verificationTokenDao, times(0)).getByEmail(email, type);
        verify(verificationTokenDao, times(0)).getByToken(token);

    }

    @Test
    public void isPresentTest() {
        String email = "arusich2008@ukr.net";
        String token = "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        VerificationToken verificationToken =
                new VerificationToken(email, type, token);

        when(verificationTokenDao.getByToken(token)).thenReturn(verificationToken);
        when(verificationTokenDao.getByEmail(email, type)).thenReturn(verificationToken);
        when(verificationTokenDao.getToken(verificationToken)).thenReturn(Optional.of(verificationToken));

        VerificationToken gottenByToken = verificationTokenService.getByToken(token).get();
        VerificationToken gottenByEmail = verificationTokenService.getByEmail(email, type);
        VerificationToken gottenByVerificationToken = verificationTokenService.getToken(verificationToken).get();

        assertEquals(verificationToken, gottenByEmail);
        assertEquals(verificationToken, gottenByToken);
        assertEquals(verificationToken, gottenByVerificationToken);

        verify(verificationTokenDao, times(1)).getByEmail(email, type);
        verify(verificationTokenDao, times(1)).getByToken(token);
        verify(verificationTokenDao, times(1)).getToken(verificationToken);
    }

    @Test
    public void generateTokenTest() {
        String email = "arusich2008@ukr.net";
        String token = "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        VerificationToken verificationToken =
                new VerificationToken(email, type, token);

        when(verificationTokenDao.getByEmail(email, type)).thenReturn(verificationToken);
        when(verificationTokenDao.save(verificationToken)).thenReturn(verificationToken);
        when(verificationTokenService.update(verificationToken)).thenReturn(verificationToken);

        VerificationToken gotten = verificationTokenService.getByEmail(email, type);
        verificationTokenService.generateToken(verificationToken);

        assertEquals("these object have to matchrd", verificationToken, gotten);
        assertNotNull("VerificationToken object has not to be null", verificationToken);
        assertNotNull("token has not to be null", verificationToken.getToken());

        verify(verificationTokenDao, atMost(1)).save(verificationToken);
        verify(verificationTokenDao, atMost(1)).getByEmail(email, type);
    }


    @Test
    public void registrateNewToken_WhenEmailPresent_ShouldReturnVerificationToken() {
        String email = "slav9nin2009@gmail.com";
        String token = "3b20d2eb44c5ac43ad2086a2683bb76e2666fd331bf4958c246aae6ff42a5e87";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        LocalDateTime localDateTime = LocalDateTime.now();
        VerificationToken verificationToken =
                new VerificationToken(email, type, token);
        verificationToken.setExpiredTime(localDateTime);

        when(verificationTokenDao.save(verificationToken)).thenReturn(verificationToken);
        doNothing().when(emailService).sentVerificationToken(email, type, token);


        VerificationToken verifiedToken = verificationTokenService.registrateNewToken(verificationToken);

        InOrder inOrder = inOrder(verificationTokenDao);
        inOrder.verify(verificationTokenDao).save(verificationToken);

        verify(verificationTokenDao, times(1)).save(verificationToken);

        assertNotNull(verifiedToken);

    }

    @Test
    public void resentToken_WhenTokenIsPresent_ThenReturnUpdatedRowWithNewToken() { //TODO

        String email = "arusich2008@ukr.net";
        String token = "3b20d2eb44c5ac43ad2086a2683bb76e2666fd331bf4958c246aae6ff42a5e87";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        LocalDateTime localDateTime = LocalDateTime.now();
        VerificationToken verificationToken =
                new VerificationToken(email, type);
        verificationToken.setExpiredTime(localDateTime);

        when(verificationTokenDao.update(verificationToken)).thenReturn(verificationToken);
        doNothing().when(emailService).sentVerificationToken(verificationToken.getEmail(), type,  verificationToken.getToken());

        VerificationToken verifiedToken = verificationTokenService.resentToken(verificationToken);

        doNothing().when(emailService).sentVerificationToken(verificationToken.getEmail(), type, verificationToken.getToken());

        assertNotNull(verifiedToken.getToken());
        assertNotEquals(token, verificationToken.getToken());

        verify(verificationTokenDao, times(1)).update(verificationToken);
    }

    @Test(expected = NotFoundException.class)
    public void exceptionWhenCreateResetPasswordToken_ForNonexistentEmail() {

        String email = "lalala@lala.com";
        String token = "3b20d2eb44c5ac43ad2086a2683bb76e2666fd331bf4958c246aae6ff42a5e87";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.PASSWORD_RESET;
        LocalDateTime localDateTime = LocalDateTime.now();
        VerificationToken verificationToken =
                new VerificationToken(email, type);
        verificationToken.setExpiredTime(localDateTime);
        verificationToken.setToken(token);

        when(userService.findByEmail(email)).thenReturn(Optional.empty());
        when(verificationTokenService.registrateNewToken(verificationToken));
    }

}
