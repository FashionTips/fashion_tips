package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Mock
    private TagLineDao tagLineDao;

    @Mock
    private ClothesDao clothesDao;

    @Mock
    private TagTypeDao tagTypeDao;

    @Mock
    private PostDao postDao;

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

    @Test(expected = NotFoundException.class)
    public void testSave_whenPostDoesNotExist_shouldThrowException() throws Exception {

        long postId = 523L;
        when(postDao.exists(1L)).thenReturn(false);
        tagLineService.save(new TagLine(), postId);
        fail("Should throw exception, when post does not exist.");
    }

    @Test(expected = NullPointerException.class)
    public void testSave_whenTagLineIsNull_shouldThrowException() throws Exception {
        tagLineService.save(null, 243L);
        fail("Should throw exception, when tag line is null.");
    }

    @Test
    public void testSave_whenArgsAreCorrect_shouldSaveTagLineToDao() throws Exception {

        long postId = 523L;
        TagLine tagLine = new TagLine();
        Post post = new Post();

        when(postDao.exists(postId)).thenReturn(true);
        when(postDao.getReference(postId)).thenReturn(post);

        tagLineService.save(tagLine, postId);

        InOrder inOrderPostDao = inOrder(postDao);
        inOrderPostDao.verify(postDao).exists(postId);
        inOrderPostDao.verify(postDao).getReference(postId);
        verify(tagLineDao, times(1)).save(tagLine);
    }

    @Test
    public void testGet() throws Exception {
        long id = 523L;
        tagLineService.get(id);
        verify(tagLineDao, times(1)).getById(id);
    }
}