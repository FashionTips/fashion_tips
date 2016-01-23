package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Tag;
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

import static com.bionicuniversity.edu.fashiontips.TagTestData.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

/**
 * Class for testing TagDao
 */
@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class TagDaoTest {

    @Inject
    private TagDao tagDao;

    @Test
    public void testGetAll() throws Exception {
        List<Tag> testList = tagDao.getAll();
        assertReflectionEquals(LIST_OF_TAGS, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    @Transactional
    public void testSaveTag() {
        Tag testTag = tagDao.save(TAG_MATCHER.deepClone(NEW_TAG_BEFORE_SAVE));
        assertReflectionEquals(NEW_TAG_AFTER_SAVE, testTag, IGNORE_DEFAULTS);
        List<Tag> testList = tagDao.getAll();
        assertReflectionEquals(LIST_WITH_NEW_TAG, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    //why don't work with @transactional
    public void testUpdateTag() {
        Tag tag = tagDao.getById(2L);
        tag.setTagParameters(Arrays.asList(NEW_TAG_PARAMETER));
        tag.setTagType(TAG_TYPE_BRAND);
        tag.setValue("test value");
        Tag updatedTag = tagDao.save(tag);
        assertReflectionEquals(UPDATED_TAG, updatedTag, IGNORE_DEFAULTS);

        List<Tag> testListOfTags = tagDao.getAll();
        assertReflectionEquals(LIST_WITH_UPDATED_SCND_TAG, testListOfTags, IGNORE_DEFAULTS, LENIENT_ORDER);

    }

    @Test
    @Transactional
    public void testDeleteTag() {
        tagDao.delete(2L);
        List<Tag> testList = tagDao.getAll();
        assertReflectionEquals(LIST_IF_DELETE_SCND_TAG, testList, IGNORE_DEFAULTS, LENIENT_ORDER);
    }
}
