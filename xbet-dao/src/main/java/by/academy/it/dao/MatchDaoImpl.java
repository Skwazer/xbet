package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.Match} entity class and has access to the 'matches' database table.
 *
 */
public class MatchDaoImpl implements MatchDao {

    private static final Logger logger = LoggerFactory.getLogger(MatchDaoImpl.class);
    private ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.matches " +
            "(date, team1_id, team2_id, `1`, X, `2`, `1X`, `12`, `2X`) " +
    "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE xbet.matches SET date = ?, team1_id = ?, team2_id = ?," +
            " `1` = ?, X = ?, `2` = ?, `1X` = ?, `12` = ?, `2X` = ? WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.matches WHERE id = ?";
    private static final String GET_UNPLAYED_MATCHES_QUERY = "SELECT * FROM xbet.matches WHERE id NOT IN " +
            "(SELECT m.id FROM  xbet.matches m JOIN xbet.results r ON m.id = r.matches_id) " +
            "ORDER BY date limit ?, 10";
    private static final String GET_MATCHES_QUERY = "SELECT * FROM xbet.matches LIMIT ?, 10";
    private static final String GET_AMOUNT_OF_UNPLAYED_MATCHES_QUERY = "SELECT COUNT(*) FROM xbet.matches WHERE id " +
            "NOT IN (SELECT m.id FROM  xbet.matches m JOIN xbet.results r ON m.id = r.matches_id)";
    private static final String GET_AMOUNT_OF_ALL_MATCHES_QUERY = "SELECT COUNT(*) FROM xbet.matches";
    private static final String DELETE_QUERY = "DELETE FROM xbet.matches WHERE id = ?";


    /**
     * Constructs an instance of {@code MatchDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public MatchDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
    }


    /**
     * Creates a new match entry in the database.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(Match match) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY))
        {
            statement.setDate(1, match.getDate());
            statement.setInt(2, match.getTeam1_id());
            statement.setInt(3, match.getTeam2_id());
            statement.setDouble(4, match.getVictory1());
            statement.setDouble(5, match.getDraw());
            statement.setDouble( 6, match.getVictory2());
            statement.setDouble( 7, match.getVictory1OrDraw());
            statement.setDouble( 8, match.getVictory1Or2());
            statement.setDouble( 9, match.getVictory2OrDraw());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao cannot create a match in DAO", e);
            throw new DAOException("MatchDao cannot create a match", e);
        }
    }


    /**
     * Retrieves a match entry by id from the database.
     *
     * @param id the id of a match.
     * @return {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public Match findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Match match = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                match = new Match();
                match.setId(set.getInt(Constants.ID));
                match.setDate(set.getDate(Constants.DATE));
                match.setTeam1_id(set.getInt(Constants.TEAM1_ID));
                match.setTeam2_id(set.getInt(Constants.TEAM2_ID));
                match.setVictory1(set.getDouble(Constants.VICTORY1));
                match.setDraw(set.getDouble(Constants.DRAW));
                match.setVictory2(set.getDouble(Constants.VICTORY2));
                match.setVictory1OrDraw(set.getDouble(Constants.VICTORY1_OR_DRAW));
                match.setVictory1Or2(set.getDouble(Constants.VICTORY1_OR_2));
                match.setVictory2OrDraw(set.getDouble(Constants.VICTORY2_OR_DRAW));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao find by id operation is failed", e);
            throw new DAOException("MatchDao find by id operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return match;
    }


    /**
     * Retrieves a list of unplayed matches from the database.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Match>} - the list of matches.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Match> getUnplayedMatches(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Match> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_UNPLAYED_MATCHES_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            Match match;
            while (set.next()) {
                match = new Match();
                match.setId(set.getInt(Constants.ID));
                match.setDate(set.getDate(Constants.DATE));
                match.setTeam1_id(set.getInt(Constants.TEAM1_ID));
                match.setTeam2_id(set.getInt(Constants.TEAM2_ID));
                match.setVictory1(set.getDouble(Constants.VICTORY1));
                match.setDraw(set.getDouble(Constants.DRAW));
                match.setVictory2(set.getDouble(Constants.VICTORY2));
                match.setVictory1OrDraw(set.getDouble(Constants.VICTORY1_OR_DRAW));
                match.setVictory1Or2(set.getDouble(Constants.VICTORY1_OR_2));
                match.setVictory2OrDraw(set.getDouble(Constants.VICTORY2_OR_DRAW));
                list.add(match);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao get matches operation is failed", e);
            throw new DAOException("MatchDao get matches operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves amount of unplayed matches from the database.
     *
     * @return amount of unplayed matches.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfUnplayedMatches() throws DAOException {
        int amount = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.createStatement();
            set = statement.executeQuery(GET_AMOUNT_OF_UNPLAYED_MATCHES_QUERY);
            if (set.next()) {
                amount = set.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao get amount of matches operation is failed", e);
            throw new DAOException("MatchDao get amount of matches operation is failed", e);
        }  finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return amount;
    }


    /**
     * Retrieves a list of all matches from the database.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Match>} - the list of unplayed matches.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Match> getMatches(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Match> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_MATCHES_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            Match match;
            while (set.next()) {
                match = new Match();
                match.setId(set.getInt(Constants.ID));
                match.setDate(set.getDate(Constants.DATE));
                match.setTeam1_id(set.getInt(Constants.TEAM1_ID));
                match.setTeam2_id(set.getInt(Constants.TEAM2_ID));
                match.setVictory1(set.getDouble(Constants.VICTORY1));
                match.setDraw(set.getDouble(Constants.DRAW));
                match.setVictory2(set.getDouble(Constants.VICTORY2));
                match.setVictory1OrDraw(set.getDouble(Constants.VICTORY1_OR_DRAW));
                match.setVictory1Or2(set.getDouble(Constants.VICTORY1_OR_2));
                match.setVictory2OrDraw(set.getDouble(Constants.VICTORY2_OR_DRAW));
                list.add(match);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao get matches operation is failed", e);
            throw new DAOException("MatchDao get matches operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }

    /**
     * Retrieves amount of all matches from the database.
     *
     * @return amount of all matches.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfAllMatches() throws DAOException {
        int amount = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.createStatement();
            set = statement.executeQuery(GET_AMOUNT_OF_ALL_MATCHES_QUERY);
            if (set.next()) {
                amount = set.getInt(1);
            }
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
     * Deletes a match entry from the database.
     *
     * @param id the {@link by.academy.it.entity.Match} id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(int id) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao cannot delete a match in DAO", e);
            throw new DAOException("MatchDao cannot delete a match", e);
        }
    }


    /**
     * Updates a match entry in the database.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(Match match) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY))
        {
            statement.setDate(1, match.getDate());
            statement.setInt(2, match.getTeam1_id());
            statement.setInt(3, match.getTeam2_id());
            statement.setDouble(4, match.getVictory1());
            statement.setDouble(5, match.getDraw());
            statement.setDouble( 6, match.getVictory2());
            statement.setDouble( 7, match.getVictory1OrDraw());
            statement.setDouble( 8, match.getVictory1Or2());
            statement.setDouble( 9, match.getVictory2OrDraw());
            statement.setInt(10, match.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("MatchDao cannot update a match in DAO", e);
            throw new DAOException("MatchDao cannot update a match", e);
        }
    }

}
