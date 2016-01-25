package com.bionicuniversity.edu.fashiontips.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.bionicuniversity.edu.fashiontips.ImageTestData.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


/**
 * Class for testing ImageDao
 *
 * @author Volodymyr Portianko
 */
@ActiveProfiles({"dev", "initImgFolder"})
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class ImageDaoTest {

    @Inject
    private ImageDao imageDao;

    @Value("${application.images.path}")
    private String imageDirectory;

    @Test
    @Transactional
    public void testSave() throws Exception {
        Path testImagePath = Paths.get(
                String.format("%s%s%s", imageDirectory, TEST_IMAGE_DIRECTORY, IMAGE_NEW.getImgName()));
        IMAGE_NEW.setData(Files.readAllBytes(testImagePath));
        imageDao.save(IMAGE_NEW);
        Path createdFile = Paths.get(imageDirectory + IMAGE_NEW.getImgName());
        Files.delete(createdFile);
        assertReflectionEquals(IMAGE_NEW, imageDao.getById(IMAGE_NEW_ID));
    }
}