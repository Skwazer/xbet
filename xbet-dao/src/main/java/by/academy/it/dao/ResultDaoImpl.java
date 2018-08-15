package by.academy.it.dao;

import by.academy.it.entity.Result;
import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Works with a {@link by.academy.it.entity.Result} entity class and has access to the 'results' database table.
 *
 */
public class ResultDaoImpl implements ResultDao {

    private static final Logger logger = LoggerFactory.getLogger(ResultDaoImpl.class);
    private static ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.results " +
            "(matches_id, result, winner_id, loser_id, winner_goals, loser_goals) " +
            "VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.results WHERE matches_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.results WHERE matches_id = ?";


    /**
     * Constructs an instance of {@code ResultDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public ResultDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
    }


    /**
     * Creates a new result entry in the database.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(Result result) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(CREATE_QUERY);
            statement.setInt(1, result.getMatchId());
            statement.setString(2, result.getResult().toString());
            statement.setInt(3, result.getWinnerId());
            statement.setInt(4, result.getLoserId());
            statement.setInt(5, result.getWinnerGoals());
            statement.setInt( 6, result.getLoserGoals());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao cannot create a result in DAO", e);
            throw new DAOException("ResultDao cannot create a result", e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }


    /**
     * Retrieves a result entry by match id from the database.
     *
     * @param matchId the id of a match.
     * @return {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public Result findByMatchId(int matchId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Result result = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, matchId);
            set = statement.executeQuery();
            if (set.next()) {
                result = new Result();
                result.setId(set.getInt(ID));
                result.setMatchId(set.getInt(MATCHES_ID));
                result.setResult(set.getString(RESULT).charAt(0));
                result.setWinnerId(set.getInt(WINNER_ID));
                result.setLoserId(set.getInt(LOSER_ID));
                result.setWinnerGoals(set.getInt(WINNER_GOALS));
                result.setLoserGoals(set.getInt(LOSER_GOALS));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao find by match id operation is failed", e);
            throw new DAOException("ResultDao find by match id operation is failed", e);
        } finally {
            closeResultSet(set);
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }


    /**
     * Deletes a result entry from the database.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(Result result) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, result.getMatchId());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao cannot delete a result in DAO", e);
            throw new DAOException("ResultDao cannot delete a result", e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }
}
