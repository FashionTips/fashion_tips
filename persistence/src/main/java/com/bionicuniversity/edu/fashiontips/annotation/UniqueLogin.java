package com.bionicuniversity.edu.fashiontips.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be unique login.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueLoginValidator.class})
public @interface UniqueLogin {

    String message() default "User with given login has already exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}