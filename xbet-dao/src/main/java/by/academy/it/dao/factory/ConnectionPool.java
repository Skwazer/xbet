package by.academy.it.dao.factory;

import java.sql.Connection;

/**
 * The interface for connection pool implementation.
 */
public interface ConnectionPool {

    /**
     * Initializes the connection pool.
     *
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an error occurred during the operation.
     */
    void init() throws ConnectionPoolException;


    /**
     * Shutdowns the connection pool.
     *
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an error occurred during the operation.
     */
    void shutdownConnectionPool() throws ConnectionPoolException;


    /**
     * Returns an available connection.
     *
     * @return a {@code java.sql.Connection} implementation.
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an exception occurred during the operation.
     */
    Connection getConnection() throws ConnectionPoolException;

}
