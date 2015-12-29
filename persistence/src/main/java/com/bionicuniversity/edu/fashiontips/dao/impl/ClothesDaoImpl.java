package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import org.springframework.stereotype.Repository;

@Repository
public class ClothesDaoImpl extends GenericDaoImpl<Clothes, Long> implements ClothesDao {
}
