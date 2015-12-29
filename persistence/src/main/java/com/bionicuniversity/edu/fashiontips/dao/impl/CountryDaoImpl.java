package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.CountryDao;
import com.bionicuniversity.edu.fashiontips.entity.Country;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of {@code CountryDao} interface.
 *
 * @author Maksym Dolia
 * @since 20.12.2015.
 */
@Repository
public class CountryDaoImpl extends GenericDaoImpl<Country, Integer> implements CountryDao {
}
