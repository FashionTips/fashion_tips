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
import java.util.Arrays;
import java.util.List;


import static com.bionicuniversity.edu.fashiontips.TagLineTestData.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

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
        assertReflectionEquals(LIST_OF_TAG_LINES, testList,IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    @Transactional
    public void testSaveTagLine(){
        TagLine testTagLine = tagLineDao.save(TAG_LINE_MATCHER.deepClone(NEW_TAG_LINE_BEFORE_SAVE));
        assertReflectionEquals(NEW_TAG_LINE_AFTER_SAVE, testTagLine,IGNORE_DEFAULTS);
        List<TagLine> testListOfTagLines = tagLineDao.getAll();
        assertReflectionEquals(LIST_WITH_NEW_TAG_LINE, testListOfTagLines,IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    @Transactional
    public void testDeleteTagLine() {
        tagLineDao.delete(1L);
        assertReflectionEquals(Arrays.asList(TAG_LINE2, TAG_LINE3), tagLineDao.getAll(), IGNORE_DEFAULTS, LENIENT_ORDER);
    }
}
