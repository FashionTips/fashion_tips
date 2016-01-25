package com.bionicuniversity.edu.fashiontips.dao;

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

/**
 * @author Alexandr Laktionov
 */
@ActiveProfiles("postgres")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutDB.sql"},
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
    public void getTokentest() {
        //TODO
    }

    public void updateTest() {
        //TODO
    }
}
