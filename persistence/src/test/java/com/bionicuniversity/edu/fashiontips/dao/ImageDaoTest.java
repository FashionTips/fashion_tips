package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Class for testing ImageDao
 */
@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageDaoTest {

    @Inject
    private ImageDao imageDao;

    @Test
    public void testSave() throws Exception {
        Image image = new Image();
        image.setData("fsdfsd".getBytes());
        image.setImgName("testImage");
        Image created = imageDao.save(image);
        System.out.println(created.toString());
    }
}