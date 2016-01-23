package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link TagLineServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagLineServiceImplTest {

    @InjectMocks
    private TagLineService tagLineService = new TagLineServiceImpl();

    @Mock    private TagLineDao tagLineDao;

    @Mock
    private ClothesDao clothesDao;

    @Mock
    private TagTypeDao tagTypeDao;

    @Mock
    ImageService imageService;

    @Test
    public void testGetAvailableTypeOfClothes() throws Exception {
        tagLineService.getAvailableTypeOfClothes();
        verify(clothesDao, times(1)).getAll();
    }

    @Test
    public void testGetExistingTagTypes() throws Exception {
        tagLineService.getExistingTagTypes();
        verify(tagTypeDao, times(1)).getAll();
    }

    @Test(expected = NullPointerException.class)
    public void testSave_whenTagLineIsNull_shouldThrowException() throws Exception {
        tagLineService.save(null, 100L, new User());
        fail("Should throw exception, when tag line is null.");
    }

    @Test(expected = NullPointerException.class)
    public void testSave_whenTagLineIsNull_shouldThrowError() throws Exception {
        tagLineService.save(new TagLine(), 10L, new User());
        fail("Should throw exception, when tag line is null.");
    }

    @Test
    public void testSave_whenUserIsWrong_shouldSaveTagLineToDao() throws Exception {

        TagLine tagLine = new TagLine();
        Long imageId = 100L;
        Optional<Image> image = Optional.of(new Image());
        User loggedUser = new User();
        image.get().setOwner(new User());

        when(imageService.findOne(imageId)).thenReturn(image);
        tagLineService.save(tagLine, imageId, loggedUser);

        verify(tagLineDao, times(1)).save(tagLine);
    }

    @Test
    public void testGet() throws Exception {
        long id = 523L;
        tagLineService.get(id);
        verify(tagLineDao, times(1)).getById(id);
    }

    @Test
    public void testDelete_whenArgsAreCorrect() throws Exception {
        long id = 500L;
        User user = new User();
        TagLine tagLine = new TagLine();
        tagLine.setImage(new Image());
        tagLine.getImage().setOwner(user);

        when(tagLineDao.getById(id)).thenReturn(tagLine);
        tagLineService.delete(id, user);
        verify(tagLineDao, times(1)).getById(id);
        verify(tagLineDao, times(1)).delete(id);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete_whenIdIsNotExist() throws Exception {;
        tagLineService.delete(10L, new User());
        fail("Should throw exception, when tag line is null.");
    }
}