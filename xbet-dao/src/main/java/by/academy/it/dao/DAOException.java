package by.academy.it.dao;

/**
 * This exception is thrown when an exception occurs during {@code Dao} instances work.
 *
 */
public class DAOException extends Exception {

    /**
     * Constructs an instance of {@code DAOException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause exception.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructs an instance of {@code DAOException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public DAOException(String message) {
        super(message);
    }
}
