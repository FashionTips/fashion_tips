package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.*;

import static com.bionicuniversity.edu.fashiontips.ServiceTestData.*;
import static org.junit.Assert.assertEquals;

/**
 * Class for testing TagService
 * @author Sergiy
 */

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml"
})
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceTest {
    @Inject
    TagService tagService;

    @Test
    public void testSaveTags() throws Exception {
        Set<Tag> test = tagService.saveTags(TAG_NAMES);
        assertEquals(TAGS, test);
    }

    @Test
    public void testGetByName() throws Exception {
        Tag test = tagService.getByName("tag1");
        assertEquals(TAG1, test);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InGetByName_IfNameDoesNotExist() throws NotFoundException {
        /*Get Tag which not exist in DB*/
        tagService.getByName("tttttt");
    }
}