package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Country;

import java.util.List;

/**
 * Service to manage business logic about consistent, not changeable data.
 *
 * @author Maksym Dolia
 * @since 23.12.2015.
 */
public interface DictionaryService {

    /**
     * Returns list of all world countries.
     *
     * @return list of countries
     */
    List<Country> findAllCountries();
}