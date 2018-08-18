package by.academy.it.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Util class, contains some useful methods.
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);


    /**
     * Rollbacks a transaction.
     *
     * @param connection a {@code java.sql.Connection} implementation.
     */
    static void rollbackConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Cannot rollback a transaction");
            }
        }
    }


    /**
     * Enables connection auto-commit mode.
     *
     * @param connection a {@code java.sql.Connection} implementation.
     */
    static void enableAutoCommit(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Cannot enable connection auto-commit");
            }
        }
    }


    /**
    * Closes a connection.
    *
    * @param connection a {@code java.sql.Connection} implementation.
    */
    static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close a connection");
            }
        }
    }


    /**
     * Closes a statement.
     *
     * @param statement a {@code java.sql.Statement} implementation.
     */
    static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Cannot close a statement");
            }
        }
    }


    /**
     * Closes a resultSet.
     *
     * @param resultSet a {@code java.sql.ResultSet} implementation.
     */
    static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Cannot close a resultSet");
            }
        }
    }

}
