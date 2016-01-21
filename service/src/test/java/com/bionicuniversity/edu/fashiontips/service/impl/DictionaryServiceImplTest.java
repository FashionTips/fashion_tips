package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CountryDao;
import com.bionicuniversity.edu.fashiontips.service.DictionaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;

/**
 * The test cases for {@link DictionaryServiceImpl} class.
 *
 * @author Maksym Dolia
 * @since 20.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DictionaryServiceImplTest {

    @InjectMocks
    private DictionaryService dictionaryService = new DictionaryServiceImpl();

    @Mock
    private CountryDao countryDao;

    @Test
    public void testFindAllCountries_shouldReturnListOfCountries() throws Exception {
        dictionaryService.findAllCountries();
        verify(countryDao, atMost(1)).getAll();
    }
}