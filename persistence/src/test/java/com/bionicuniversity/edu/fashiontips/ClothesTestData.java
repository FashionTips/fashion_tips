package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;

/**
 * @author Alexandr Laktionov
 */
public class ClothesTestData {

    public static final ModelMatcher<Clothes, String> CLOTHES_MATCHER = new ModelMatcher<>(Clothes::toString);

    public static final Clothes CLOTHES_DRESS = new Clothes(4L, "dress");

}
