package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static com.bionicuniversity.edu.fashiontips.VerificationTokenTestData.*;
import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * @author Alexandr Laktionov
 */
@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class VerificationTokenTest {

    @Inject
    private UserDao userDao;

    @Inject
    private VerificationTokenDao verificationTokenDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getByEmailIfPresentTest() {
        String email = "arusich2008@ukr.net";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        VerificationToken verificationToken = verificationTokenDao.getByEmail(email, type);
        assertReflectionEquals(ArusichVerificationTokenAndNotVerified, verificationToken);
    }

    @Test
    public void getByEmailIfNotPresentTest() {
        String email = "arusich777@ukr.net";
        VerificationTokenPK.Type type = VerificationTokenPK.Type.EMAIL_VERIFICATION;
        VerificationToken verificationToken = verificationTokenDao.getByEmail(email, type);
        assertNull(verificationToken);
    }

    @Test
    public void getByTokenTest() {
        String token = "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261";
        String badToken = "0000000c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261";
        VerificationToken verificationToken = verificationTokenDao.getByToken(token);
        VerificationToken verificationTokenBad = verificationTokenDao.getByToken(badToken);
        assertReflectionEquals(ArusichVerificationTokenAndNotVerified, verificationToken);
        assertNull(verificationTokenBad);
    }

    @Test
    public void saveTest() {
        VerificationToken verificationToken = new VerificationToken("slav9nin2009@gmail.com", EMAIL_VERIFICATION);
        verificationTokenDao.save(verificationToken);
        assertNotNull(verificationToken);
        assertEquals(NewVerificationToken.getEmail(), verificationToken.getEmail());
        assertFalse(verificationToken.isVerified());
        assertNotNull(verificationToken.getExpiredTime());
        assertTrue(verificationToken.getExpiredTime().isAfter(LocalDateTime.now()));
        VerificationToken newToken =
                new VerificationToken("email4@example.com", EMAIL_VERIFICATION,
                        "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000004444");
        newToken.setExpiredTime(LOCAL_DATE_TIME_NOW);
        verificationTokenDao.save(newToken);
        assertReflectionEquals(NotPresentedInUserToken, newToken);


    }

    @Test
    public void getTokenTest() {
        VerificationToken verificationToken =
                verificationTokenDao.getToken(ArusichVerificationTokenAndNotVerified).get();
        assertReflectionEquals(ArusichVerificationTokenAndNotVerified, verificationToken);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenUnAvailableTest() {
        VerificationToken verificationToken =
                verificationTokenDao.getToken(NullableVerificationToken).get();
    }

    @Test
    public void updateTest() {
        VerificationToken token = verificationTokenDao
                .getByToken("b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
        token.setVerified(true);
        verificationTokenDao.update(token);
        VerificationToken updatedToken = verificationTokenDao.getToken(token).get();
        assertReflectionEquals(ArusichVerificationTokenAndVerified, updatedToken);
        updatedToken.setExpiredTime(LocalDateTime.now());
        updatedToken.setVerified(false);
        verificationTokenDao.update(updatedToken);
        VerificationToken secondUpdatedToken = verificationTokenDao.getToken(updatedToken).get();
        assertNotNull(secondUpdatedToken.getExpiredTime());
        assertFalse(secondUpdatedToken.isVerified());
    }

    @Test
    public void getTokenWithBadParamTets() {
        VerificationToken token = verificationTokenDao.getByToken(ArusichVerificationTokenWithBadToken.getToken());
        assertNull(token);
    }
}
