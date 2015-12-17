package com.bionicuniversity.edu.fashiontips.annotation;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Constraint validator for custom {@code UniqueEmail} annotation.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Inject
    private UserDao userDao;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // NOP
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        /* sometimes the instance can be created without injection of userDao.
        So we have to check, whether this is that case. */
        if(userDao == null) {
            return true;
        }

        return userDao.findByEmail(email) == null;
    }
}
