package com.bionicuniversity.edu.fashiontips.api;


import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Class to handle exceptions in global app scope.
 *
 * @author Maksym Dolia
 * @author Vadym Golub
 * @since 29.11.2015.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when requests have to be ended with http status 404 (NOT FOUND)
     *
     * @param e exception
     * @return response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInformation> processUserNotFoundEx(Exception e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handler for all unspecified exceptions.
     *
     * @param e exception
     * @return response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInformation> processUnspecifiedException(Exception e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation exceptions.
     *
     * @param e exception
     * @return response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInformation> processValidationException(ConstraintViolationException e) {

        StringBuilder stringBuilder = new StringBuilder();

        /* build message with all validation errors */
        e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .map(str -> str + " ")
                .forEach(stringBuilder::append);
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), stringBuilder.toString());
        return ResponseEntity.unprocessableEntity().body(error);
    }
}