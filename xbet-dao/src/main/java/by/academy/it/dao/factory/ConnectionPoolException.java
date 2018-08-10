package by.academy.it.dao.factory;

/**
 * This exception is thrown when an exception occurs during the connection pool work.
 *
 */
public class ConnectionPoolException extends Exception {

    /**
     * Constructs an instance of {@code ConnectionPoolException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ConnectionPoolException(String message) {
        super(message);
    }


    /**
     * Constructs an instance of {@code ConnectionPoolException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause exception.
     */
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
