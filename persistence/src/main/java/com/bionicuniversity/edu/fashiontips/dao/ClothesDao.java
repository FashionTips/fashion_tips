package com.bionicuniversity.edu.fashiontips.dao;


import com.bionicuniversity.edu.fashiontips.entity.Clothes;

public interface ClothesDao extends GenericDao<Clothes, Long> {

    Clothes findClothesByName(String clothesName);

}
