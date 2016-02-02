package com.bionicuniversity.edu.fashiontips.annotation;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Constraint validator for custom annotation {@code UniqueLogin}.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {


    private UserDao userDao;

    @Inject
    public UniqueLoginValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
        //NOP
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userDao.getByLogin(value) == null;
    }
}
