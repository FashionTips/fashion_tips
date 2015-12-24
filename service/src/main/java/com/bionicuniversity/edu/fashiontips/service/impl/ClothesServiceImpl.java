package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.service.ClothesService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Alexandr Laktionov
 */
@Named
public class ClothesServiceImpl implements ClothesService {

    @Inject
    private ClothesDao clothesDao;

    @Override
    public Clothes findClothesByName(String clothesName) {
        Clothes clothes = clothesDao.findClothesByName(clothesName);
        if (clothes != null) {
            return clothes;
        } else {
            throw new NotFoundException(String.format("Clothes tag with tag's name %s not found", clothesName));
        }
    }
}
