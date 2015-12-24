package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;

/**
 * @author Alexandr Laktionov
 */
public interface ClothesService {

    Clothes findClothesByName(String clothesName);

}
