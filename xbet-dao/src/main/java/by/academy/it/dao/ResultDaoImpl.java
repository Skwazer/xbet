package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.Result} entity class and has access to the 'results' database table.
 *
 */
public class ResultDaoImpl implements ResultDao {

    private static final Logger logger = LoggerFactory.getLogger(ResultDaoImpl.class);
    private ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.results " +
            "(matches_id, result, team1_goals, team2_goals) VALUES ( ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE xbet.results SET " +
            "matches_id = ?, result = ?, team1_goals = ?, team2_goals = ? WHERE id = ?";
    private static final String GET_RESULTS_QUERY = "SELECT * FROM xbet.results LIMIT ?, 10";
    private static final String GET_AMOUNT_OF_ALL_RESULTS_QUERY = "SELECT COUNT(*) FROM xbet.results";
    private static final String GET_LAST_RESULTS_QUERY = "SELECT * FROM xbet.results ORDER BY id DESC LIMIT ?, 10";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.results WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.results WHERE id = ?";


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
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY))
        {
            statement.setInt(1, result.getMatchId());
            statement.setString(2, result.getResult());
            statement.setInt(3, result.getTeam1_goals());
            statement.setInt( 4, result.getTeam2_goals());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao cannot create a result in DAO", e);
            throw new DAOException("ResultDao cannot create a result", e);
        }
    }


    /**
     * Retrieves a result entry by id from the database.
     *
     * @param id the id of a result.
     * @return {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public Result findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Result result = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                result = new Result();
                result.setId(set.getInt(Constants.ID));
                result.setMatchId(set.getInt(Constants.MATCHES_ID));
                result.setResult(set.getString(Constants.RESULT));
                result.setTeam1_goals(set.getInt(Constants.TEAM1_GOALS));
                result.setTeam2_goals(set.getInt(Constants.TEAM2_GOALS));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao find by id operation is failed", e);
            throw new DAOException("ResultDao find by id operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return result;
    }


    /**
     * Retrieves a list of all results from the database.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Result>} - the list of all results.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Result> getResults(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Result> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_RESULTS_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            Result result;
            while (set.next()) {
                result = new Result();
                result.setId(set.getInt(Constants.ID));
                result.setMatchId(set.getInt(Constants.MATCHES_ID));
                result.setResult(set.getString(Constants.RESULT));
                result.setTeam1_goals(set.getInt(Constants.TEAM1_GOALS));
                result.setTeam2_goals(set.getInt(Constants.TEAM2_GOALS));
                list.add(result);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao get last results operation is failed", e);
            throw new DAOException("ResultDao get last results operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves a list of last results from the database.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Result>} - the list of all results.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Result> getLastResults(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Result> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_LAST_RESULTS_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            Result result;
            while (set.next()) {
                result = new Result();
                result.setId(set.getInt(Constants.ID));
                result.setMatchId(set.getInt(Constants.MATCHES_ID));
                result.setResult(set.getString(Constants.RESULT));
                result.setTeam1_goals(set.getInt(Constants.TEAM1_GOALS));
                result.setTeam2_goals(set.getInt(Constants.TEAM2_GOALS));
                list.add(result);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao get results operation is failed", e);
            throw new DAOException("ResultDao get results operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves amount of all results from the database.
     *
     * @return amount of all results.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfAllResults() throws DAOException {
        int amount = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.createStatement();
            set = statement.executeQuery(GET_AMOUNT_OF_ALL_RESULTS_QUERY);
            if (set.next()) {
                amount = set.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao get amount of results operation is failed", e);
            throw new DAOException("ResultDao get amount of results operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return amount;
    }


    /**
     * Deletes a result entry from the database.
     *
     * @param id the {@link by.academy.it.entity.Result} id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(int id) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao cannot delete a result in DAO", e);
            throw new DAOException("ResultDao cannot delete a result", e);
        }
    }


    /**
     * Updates a result entry in the database.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(Result result) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY))
        {
            statement.setInt(1, result.getMatchId());
            statement.setString(2, result.getResult());
            statement.setInt(3, result.getTeam1_goals());
            statement.setInt( 4, result.getTeam2_goals());
            statement.setInt(5, result.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("ResultDao cannot update a result in DAO", e);
            throw new DAOException("ResultDao cannot update a result", e);
        }
    }


}
