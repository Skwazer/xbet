package by.academy.it.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Root Dao interface. Defines default Dao methods.
 */
public interface Dao {

    Logger logger = LoggerFactory.getLogger(Dao.class);


    /**
    * Closes a connection.
    *
    * @param connection a {@code java.sql.Connection} implementation.
    */
    default void closeConnection(Connection connection) {
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
    default void closeStatement(Statement statement) {
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
    default void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Cannot close a resultSet");
            }
        }
    }

}
