package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;


import static com.bionicuniversity.edu.fashiontips.TagLineTestData.*;

/**
 * Class for testing TagLineDao
 */


@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class TagLineDaoTest {

    @Inject
    private TagLineDao tagLineDao;

    @Test
    public void testGetAll(){
        List<TagLine> testList = tagLineDao.getAll();
        TAG_LINE_MATCHER.assertListEquals(LIST_OF_TAG_LINES, testList);
    }

    @Test
    @Transactional
    public void saveTagLine(){
        TagLine testTagLine = tagLineDao.save(TAG_LINE_MATCHER.deepClone(NEW_TAG_LINE_BEFORE_SAVE));
        TAG_LINE_MATCHER.assertEquals(NEW_TAG_LINE_AFTER_SAVE, testTagLine);
        List<TagLine> testListOfTagLines = tagLineDao.getAll();
        TAG_LINE_MATCHER.assertListEquals(LIST_WITH_NEW_TAG_LINE, testListOfTagLines);
    }
}