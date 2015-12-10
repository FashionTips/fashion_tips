package com.bionicuniversity.edu.fashiontips.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static com.bionicuniversity.edu.fashiontips.DaoTestData.*;
import static org.junit.Assert.assertEquals;

/**
 * Calss for testing TagDao
 */

@ActiveProfiles({"dev"})
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class TagDaoTest {

    @Inject
    private TagDao tagDao;

    @Test
    public void testGetByNameIfNameExist() throws Exception {
        assertEquals(TAG1, tagDao.getByName("tag1"));
    }

    @Test
    public void testGetByNameIfNameNotExist() throws Exception {
        assertEquals(null, tagDao.getByName("tag12345"));
    }
}