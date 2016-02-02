package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CountryDao;
import com.bionicuniversity.edu.fashiontips.entity.Country;
import com.bionicuniversity.edu.fashiontips.service.DictionaryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Implementation of {@code DictionaryService} interface.
 *
 * @author Maksym Dolia
 * @since 23.12.2015.
 */
@Named
public class DictionaryServiceImpl implements DictionaryService {

    @Inject
    private CountryDao countryDao;

    @Override
    public List<Country> findAllCountries() {
        return countryDao.getAll();
    }
}