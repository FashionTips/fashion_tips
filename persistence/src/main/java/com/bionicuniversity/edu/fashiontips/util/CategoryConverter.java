package com.bionicuniversity.edu.fashiontips.util;

import com.bionicuniversity.edu.fashiontips.entity.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Category Converter
 */

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category,String> {

    public static final String QUESTION_TYPE = Category.CategoryType.QUESTION; //"QUESTION"
    public static final String POST_TYPE = Category.CategoryType.POST; //"POST"

    @Override
    public String convertToDatabaseColumn(Category attribute) {
        switch (attribute) {
            case QUESTION: {
                return Category.QUESTION.getCategory();
            }
            case POST: {
                return Category.POST.getCategory();
            }
            default: throw new IllegalArgumentException("Required category does not present!");
        }
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case QUESTION_TYPE : {
                return Category.QUESTION;
            }
            case POST_TYPE : {
                return Category.POST;
            }
            default: throw new IllegalArgumentException("Retrieved categore does not present in system");
        }
    }
}
