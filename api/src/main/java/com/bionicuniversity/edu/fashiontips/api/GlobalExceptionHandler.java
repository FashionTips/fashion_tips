package com.bionicuniversity.edu.fashiontips.api;


import com.bionicuniversity.edu.fashiontips.service.util.exception.NotAllowedActionException;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    public ResponseEntity processNotFoundEx(Exception e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when requests have to be ended with http status 403 (FORBIDDEN)
     *
     * @param e exception
     * @return response entity
     */
    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            AccessDeniedException.class,
            NotAllowedActionException.class})
    public ResponseEntity processForbidden(Exception e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


    /**
     * Handler for all unspecified exceptions.
     *
     * @param e exception
     * @return response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity processUnspecifiedException(Exception e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handles validation exceptions.
     *
     * @param e exception
     * @return response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInformation> processValidationException(MethodArgumentNotValidException e) {

        StringBuilder stringBuilder = new StringBuilder();

        /* build message with all validation errors */
        e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .map(s -> s + " ")
                .forEach(stringBuilder::append);

        ErrorInformation error = new ErrorInformation(e.getClass().toString(), stringBuilder.toString().trim());
        return ResponseEntity.unprocessableEntity().body(error);
    }

    /**
     * Handler for exceptions, caused by bad requests from client side.
     *
     * @param e exception
     * @return response with detailed error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity badRequest(HttpMessageNotReadableException e) {
        ErrorInformation error = new ErrorInformation(e.getClass().toString(), e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
