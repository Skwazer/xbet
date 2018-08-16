package by.academy.it.service;


/**
 * This exception is thrown when an exception occurs during {@code service} instances work.
 *
 */
public class ServiceException extends Exception {

    /**
     * Constructs an instance of {@code ServiceException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ServiceException(String message) {
        super(message);
    }


    /**
     * Constructs an instance of {@code ServiceException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause exception.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
