package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Image;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Class for testing ImageDao
 */
@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageDaoTest {

    @Value("${images.path}")
    private String imageDirectory;

    @Inject
    private ImageDao imageDao;

    private Image testImage1;

    private Path testImage1Path;

    {
        testImage1 = new Image("testImage1.jpg");
        testImage1.setData("fsdfsd".getBytes());
    }

    @After
    public void onExit() throws Exception {
        if (Files.exists(testImage1Path)) Files.delete(testImage1Path);
    }

    @Test
    public void testSave() throws Exception {
        Image created = imageDao.save(testImage1);
        testImage1Path = Paths.get(imageDirectory.substring(imageDirectory.length() - 1, imageDirectory.length()).equals("/") ?
                imageDirectory + created.getImgName() : imageDirectory + "/" + created.getImgName());
        assertTrue(Files.exists(testImage1Path));
    }
}