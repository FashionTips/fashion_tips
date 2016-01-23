package com.bionicuniversity.edu.fashiontips.service.util.exception;

/**
 * For situations, where required actions cannot be done due to restrictions.
 *
 * @author Maksym Dolia
 * @since 23.01.2016.
 */
public class NotAllowedActionException extends RuntimeException {

    public NotAllowedActionException() {
    }

    public NotAllowedActionException(String message) {
        super(message);
    }

    public NotAllowedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedActionException(Throwable cause) {
        super(cause);
    }

    public NotAllowedActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
