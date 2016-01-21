package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ImageDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link ImageServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @InjectMocks
    private ImageService imageService = new ImageServiceImpl();

    @Mock
    private ImageDao imageDao;

    @Test(expected = NullPointerException.class)
    public void testSave_whenImageIsNull_shouldThrowException() throws Exception {
        imageService.save(null);
        fail("Should throw an exception, when image is null.");
    }

    @Test
    public void testSave_shouldSaveImageToDao() throws Exception {
        Image image = new Image();
        when(imageService.save(image)).thenReturn(image);
        assertEquals("The images should be the same.", image, imageService.save(image));
        verify(imageDao, atMost(1)).save(image);
    }
}