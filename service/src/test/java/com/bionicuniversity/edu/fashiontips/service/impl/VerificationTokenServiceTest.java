package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.service.EmailService;
import com.bionicuniversity.edu.fashiontips.service.VerificationTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alexandr Laktionov
 */
@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    @InjectMocks
    private VerificationTokenService verificationTokenService;

    @Mock
    private VerificationTokenDao verificationTokenDao;

    @Mock
    private EmailService emailService;

    @Test
    public void getByEmailTest() {
        //TODO
    }

    @Test
    public void getByTokenTest() {
        //TODO
    }

    @Test
    public void saveTest() {
        //TODO
    }

    @Test
    public void sendEmailRegistrationTokenTest() {
        //TODO
    }

    @Test
    public void isPresentTest() {
        //TODO
    }

    @Test
    public void getTokenTest() {
        //TODO
    }

    @Test
    public void generateTokenTest() {
        //TODO
    }

    @Test
    public void createNewTokenTest() {
        //TODO
    }
}
