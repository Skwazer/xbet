package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.Bet;
import by.academy.it.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Performs transaction operations with tables.
 *
 */
public class TransactionalDaoImpl implements TransactionalDao {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalDaoImpl.class);
    private ConnectionPool pool;

    private static final String UPDATE_USER_BALANCE_QUERY = "UPDATE xbet.users SET balance = ? WHERE login = ?";
    private static final String CREATE_BET_QUERY = "INSERT INTO xbet.bets " +
            "(user_id, match_id, bet_result, bet, money, status) VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_RESULT_QUERY = "INSERT INTO xbet.results " +
            "(matches_id, result, team1_goals, team2_goals) " +
            "VALUES ( ?, ?, ?, ?)";
    private static final String UPDATE_BET_STATUS_QUERY = "UPDATE xbet.bets SET status=? WHERE id=?";
    private static final String UPDATE_USER_BALANCE_BY_ID_QUERY =
            "UPDATE xbet.users SET balance = balance + ? WHERE id = ?";


    /**
     * Constructs an instance of {@code TransactionalDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public TransactionalDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
        logger.info("TransactionalDao has been created");
    }


    /**
     * Updates user's balance and creates a bet entry in the database.
     *
     * @param login the user login.
     * @param balance the user balance.
     * @param bet {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public void placeBet(String login, double balance, Bet bet) throws DAOException {
        Connection connection = null;
        PreparedStatement updateUserStatement = null;
        PreparedStatement createBetStatement = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            updateUserStatement = connection.prepareStatement(UPDATE_USER_BALANCE_QUERY);
            updateUserStatement.setDouble(1, balance);
            updateUserStatement.setString(2, login);
            updateUserStatement.executeUpdate();

            createBetStatement = connection.prepareStatement(CREATE_BET_QUERY);
            createBetStatement.setInt(1, bet.getUser_id());
            createBetStatement.setInt(2, bet.getMatch_id());
            createBetStatement.setString(3, bet.getBetResult());
            createBetStatement.setDouble(4, bet.getBet());
            createBetStatement.setDouble(5, bet.getMoney());
            createBetStatement.setString(6, bet.getStatus());
            createBetStatement.executeUpdate();

            connection.commit();

        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TransactionalDao cannot perform placeBet operation", e);
            Utils.rollbackConnection(connection);
            throw new DAOException("TransactionalDao cannot perform placeBet operation", e);
        } finally {
            Utils.closeStatement(updateUserStatement);
            Utils.closeStatement(createBetStatement);
            Utils.enableAutoCommit(connection);
            Utils.closeConnection(connection);
        }
    }


    /**
     * Creates a result entry, updates bet entries status and balance of winning users.
     *
     * @param result {@link by.academy.it.entity.Result} - the result of a match.
     * @param bets {@code List<Bet>} - bets placed on the match.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public void finishMatch(Result result, List<Bet> bets) throws DAOException {
        Connection connection = null;
        PreparedStatement createResultStatement = null;
        PreparedStatement updateBetStatement = null;
        PreparedStatement updateUserStatement = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            createResultStatement = connection.prepareStatement(CREATE_RESULT_QUERY);
            createResultStatement.setInt(1, result.getMatchId());
            createResultStatement.setString(2, result.getResult());
            createResultStatement.setInt(3, result.getTeam1_goals());
            createResultStatement.setInt( 4, result.getTeam2_goals());
            createResultStatement.executeUpdate();
            logger.debug("result has been created - " + result);

            updateBetStatement = connection.prepareStatement(UPDATE_BET_STATUS_QUERY);
            for (Bet bet : bets) {
                updateBetStatement.setString(1, bet.getStatus());
                updateBetStatement.setInt(2, bet.getId());
                updateBetStatement.addBatch();
                logger.debug("bet statement is prepared - " + bet);
            }
            updateBetStatement.executeBatch();

            updateUserStatement = connection.prepareStatement(UPDATE_USER_BALANCE_BY_ID_QUERY);
            for (Bet bet : bets) {
                if (bet.getStatus().equals(Constants.WON)) {
                    updateUserStatement.setDouble(1, bet.getBet() * bet.getMoney());
                    updateUserStatement.setInt(2, bet.getUser_id());
                    updateUserStatement.addBatch();
                    logger.debug("user balance: id : [" + bet.getUser_id()
                            + "], prize [" + bet.getMoney() * bet.getBet() + "]");
                }
            }
            updateUserStatement.executeBatch();

            connection.commit();

        } catch (ConnectionPoolException | SQLException e) {
            logger.error("TransactionalDao cannot perform finishMatch operation", e);
            Utils.rollbackConnection(connection);
            throw new DAOException("TransactionalDao cannot perform finishMatch operation", e);
        } finally {
            Utils.closeStatement(createResultStatement);
            Utils.closeStatement(updateBetStatement);
            Utils.closeStatement(updateUserStatement);
            Utils.enableAutoCommit(connection);
            Utils.closeConnection(connection);
        }
    }

}
