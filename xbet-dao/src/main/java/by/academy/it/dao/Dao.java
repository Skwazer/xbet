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

    String ID = "id";
    String USER_ID = "user_id";
    String MATCH_ID = "match_id";
    String BET_RESULT = "bet_result";
    String BET = "bet";
    String MONEY = "money";
    String STATUS = "status";
    String DATE = "date";
    String TEAM1_ID = "team1_id";
    String TEAM2_ID = "team2_id";
    String VICTORY1 = "1";
    String DRAW = "X";
    String VICTORY2 = "2";
    String VICTORY1_OR_DRAW = "1X";
    String VICTORY1_OR_2 = "12";
    String VICTORY2_OR_DRAW = "2X";
    String MATCHES_ID = "matches_id";
    String RESULT = "result";
    String WINNER_ID = "winner_id";
    String LOSER_ID = "loser_id";
    String WINNER_GOALS = "winner_goals";
    String LOSER_GOALS = "loser_goals";
    String ROLE = "role";
    String NAME = "name";
    String LOGIN = "login";
    String PASSWORD = "password";
    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String EMAIL = "email";
    String BALANCE = "balance";
    String ROLE_ID = "role_id";


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
