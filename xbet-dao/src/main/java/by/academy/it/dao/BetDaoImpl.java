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

    private static final String CREATE_QUERY = "INSERT INTO xbet.bets " +
            "(user_id, match_id, bet_result, bet, money, status) VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_USER_ID_QUERY = "SELECT * FROM xbet.bets WHERE user_id = ? limit ?, 10";
    private static final String GET_AMOUNT_BY_USER_ID_QUERY = "SELECT COUNT(*) FROM xbet.bets WHERE user_id = ?";
    private static final String GET_BY_MATCH_ID_QUERY =
            "SELECT * FROM xbet.bets WHERE match_id = ?  AND status = 'active'";
    private static final String DELETE_QUERY = "DELETE FROM xbet.bets WHERE match_id = ?";
    private static final String UPDATE_BET_STATUS_QUERY = "UPDATE xbet.bets SET status=? WHERE id=?";


    /**
     * Constructs an instance of {@code BetDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public BetDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
    }


    /**
     * Creates a new bet entry in the database.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(Bet bet) throws DAOException {
        try (Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY))
        {
            statement.setInt(1, bet.getUser_id());
            statement.setInt(2, bet.getMatch_id());
            statement.setString(3, bet.getBetResult());
            statement.setDouble(4, bet.getBet());
            statement.setDouble(5, bet.getMoney());
            statement.setString(6, bet.getStatus());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao cannot create a bet in DAO", e);
            throw new DAOException("BetDao cannot create a bet", e);
        }
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
            statement = connection.prepareStatement(GET_BY_USER_ID_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, startFrom);
            set = statement.executeQuery();
            Bet bet;
            while (set.next()) {
                bet = new Bet();
                bet.setId(set.getInt(Constants.ID));
                bet.setUser_id(set.getInt(Constants.USER_ID));
                bet.setMatch_id(set.getInt(Constants.MATCH_ID));
                bet.setBetResult(set.getString(Constants.BET_RESULT));
                bet.setBet(set.getDouble(Constants.BET));
                bet.setMoney(set.getDouble(Constants.MONEY));
                bet.setStatus(set.getString(Constants.STATUS));
                list.add(bet);
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
     * Retrieves amount of user bets from the database.
     *
     * @param userId the id of a user.
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfUserBets(int userId) throws DAOException {
        int amount;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_AMOUNT_BY_USER_ID_QUERY);
            statement.setInt(1, userId);
            set = statement.executeQuery();
            set.next();
            amount = set.getInt(1);
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao get amount of matches operation is failed", e);
            throw new DAOException("MatchDao get amount of matches operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return amount;
    }


    /**
     * Deletes a bet entry from the database.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(Bet bet) throws DAOException {
        try (Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1, bet.getMatch_id());
            statement.executeUpdate();
        } catch (SQLException  | ConnectionPoolException e) {
            logger.error("BetDao cannot delete a bet in DAO", e);
            throw new DAOException("BetDao cannot delete a bet", e);
        }
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
            statement = connection.prepareStatement(GET_BY_MATCH_ID_QUERY);
            statement.setInt(1, matchId);
            set = statement.executeQuery();
            Bet bet;
            while (set.next()) {
                bet = new Bet();
                bet.setId(set.getInt(Constants.ID));
                bet.setUser_id(set.getInt(Constants.USER_ID));
                bet.setMatch_id(set.getInt(Constants.MATCH_ID));
                bet.setBetResult(set.getString(Constants.BET_RESULT));
                bet.setBet(set.getDouble(Constants.BET));
                bet.setMoney(set.getDouble(Constants.MONEY));
                bet.setStatus(set.getString(Constants.STATUS));
                list.add(bet);
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
     * Updates a bet entry in the database.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public void updateStatus(Bet bet) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BET_STATUS_QUERY))
        {
            statement.setString(1, bet.getStatus());
            statement.setInt(2, bet.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("BetDao cannot create a bet in DAO", e);
            throw new DAOException("BetDao cannot create a bet", e);
        }
    }

}
