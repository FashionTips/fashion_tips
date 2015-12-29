package com.bionicuniversity.edu.fashiontips.annotation;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Constraint validator for custom annotation {@code UniqueLogin}.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    @Inject
    private UserDao userDao;

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
        //NOP
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        /* sometimes the instance can be created without injection of userDao.
            So we have to check, whether this is that case.  */
        if (userDao == null) {
            return true;
        }

        try {
            userDao.getByLogin(value);
            return false;
        } catch (NoResultException ex) {
            return true;
        }
    }
}
