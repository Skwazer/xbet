package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.Bet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.Bet} entity class and has access to the 'bets' database table.
 *
 */
public class BetDaoImpl implements BetDao {

    private static final Logger logger = LoggerFactory.getLogger(BetDaoImpl.class);
    private ConnectionPool pool;

    private static final String GET_BY_USER_ID_QUERY = "SELECT * FROM xbet.bets WHERE user_id = ? limit ?, 10";
    private static final String GET_ALL_QUERY = "SELECT * FROM xbet.bets limit ?, 10";
    private static final String GET_AMOUNT_BY_USER_ID_QUERY = "SELECT COUNT(*) FROM xbet.bets WHERE user_id = ?";
    private static final String GET_AMOUNT_OF_ALL_BETS_QUERY = "SELECT COUNT(*) FROM xbet.bets";
    private static final String GET_BY_MATCH_ID_QUERY =
            "SELECT * FROM xbet.bets WHERE match_id = ?  AND status = 'active'";


    /**
     * Constructs an instance of {@code BetDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public BetDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
        logger.info("BetDao has been created");
    }


    /**
     * Retrieves a list of bets from the database by user id.
     *
     * @param userId the id of a user.
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Bet>} - the list of user's bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Bet> findByUserId(int userId, int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Bet> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_BY_USER_ID_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, startFrom);
            set = statement.executeQuery();
            while (set.next()) {
                list.add(setBetFields(set));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao find by user id operation is failed", e);
            throw new DAOException("BetDao find by user id operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Returns bet entity with fields values retrieved from the database.
     *
     * @param set java.sql.ResultSet
     * @return {@link by.academy.it.entity.Bet} entity.
     * @throws SQLException if an exception occurred during the operation.
     */
    private Bet setBetFields(ResultSet set) throws SQLException {
        Bet bet = new Bet();
        bet.setId(set.getInt(Constants.ID));
        bet.setUser_id(set.getInt(Constants.USER_ID));
        bet.setMatch_id(set.getInt(Constants.MATCH_ID));
        bet.setBetResult(set.getString(Constants.BET_RESULT));
        bet.setBet(set.getDouble(Constants.BET));
        bet.setMoney(set.getDouble(Constants.MONEY));
        bet.setStatus(set.getString(Constants.STATUS));
        return bet;
    }


    /**
     * Retrieves amount of user bets from the database.
     *
     * @param userId the id of a user.
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfUserBets(int userId) throws DAOException {
        int amount = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_AMOUNT_BY_USER_ID_QUERY);
            statement.setInt(1, userId);
            set = statement.executeQuery();
            if (set.next()) {
                amount = set.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao get amount of user bets operation is failed", e);
            throw new DAOException("BetDao get amount of user bets operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return amount;
    }


    /**
     * Retrieves a list of bets from the database by match id.
     *
     * @param matchId the id of a {@link by.academy.it.entity.Match} entity.
     * @return {@code List<Bet>} - the list of bets placed on this Match.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public List<Bet> findByMatchId(int matchId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Bet> list = new ArrayList<>();
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_BY_MATCH_ID_QUERY);
            statement.setInt(1, matchId);
            set = statement.executeQuery();
            while (set.next()) {
                list.add(setBetFields(set));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao find by match id operation is failed", e);
            throw new DAOException("BetDao find by match id operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves a list of all bets from the database.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Bet>} - the list of all bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Bet> findAll(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Bet> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_ALL_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            while (set.next()) {
                list.add(setBetFields(set));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao find all bets operation is failed", e);
            throw new DAOException("BetDao find all bets operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves amount of all bets from the database.
     *
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfAllBets() throws DAOException {
        int amount = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_AMOUNT_OF_ALL_BETS_QUERY);
            set = statement.executeQuery();
            if (set.next()) {
                amount = set.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao get amount of all bets operation is failed", e);
            throw new DAOException("BetDao get amount of all bets operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return amount;
    }

}
