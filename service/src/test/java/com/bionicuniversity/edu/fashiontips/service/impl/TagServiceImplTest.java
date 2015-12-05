package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sergiy on 04.12.2015
 * Project: fashion-tips
 */
@ActiveProfiles("production")
@ContextConfiguration(locations = {
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml"
})
@Sql(scripts = {"classpath:db/filloutDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
/*Execute tests ordering by method name*/
@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceImplTest {

    @Inject
    private TagServiceImpl service;

    private List<String> tagsNames = new ArrayList<>();
    private Set<Tag> check = new HashSet<>();

    {
        tagsNames.add("tag1");
        tagsNames.add("tag2");
        tagsNames.add("tag3");
        tagsNames.add("tag4");
        tagsNames.add("tag5");

        Long i = 1L;
        for (String name : tagsNames) {
            Tag tag = new Tag(name);
            tag.setId(i++);
            check.add(tag);
        }
    }

    @Test
    public void testSaveTags() throws Exception {
        Set<Tag> test = service.saveTags(tagsNames);
        assertEquals(check, test);
    }

}