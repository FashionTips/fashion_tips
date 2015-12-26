package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static com.bionicuniversity.edu.fashiontips.ClothesTestData.CLOTHES_DRESS;
import static com.bionicuniversity.edu.fashiontips.ClothesTestData.CLOTHES_MATCHER;

/**
 * @author Alexandr Laktionov
 */
@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class ClothesDaoTest {

    @Inject
    private ClothesDao clothesDao;

    @Test
    public void testFindClothesByName() {
        Clothes clothes = clothesDao.findClothesByName("dress");
        CLOTHES_MATCHER.assertEquals(CLOTHES_DRESS, clothes);
    }

}
