package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.Team} entity class and has access to the 'teams' database table.
 *
 */
public class TeamDaoImpl implements TeamDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);
    private ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.teams (name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE xbet.teams SET name = ? WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.teams WHERE id = ?";
    private static final String GET_TEAMS_QUERY = "SELECT * FROM xbet.teams ORDER BY id LIMIT ?, 10";
    private static final String GET_AMOUNT_OF_ALL_TEAMS_QUERY = "SELECT COUNT(*) FROM xbet.teams";
    private static final String DELETE_QUERY = "DELETE FROM xbet.teams WHERE id = ?";


    /**
     * Constructs an instance of {@code TeamDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public TeamDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
    }


    /**
     * Creates a new team entry in the database.
     *
     * @param team the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(Team team) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY))
        {
            statement.setString(1, team.getName());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao cannot create a team in DAO", e);
            throw new DAOException("TeamDao cannot create a team", e);
        }
    }


    /**
     * Retrieves a team entry by id from the database.
     *
     * @param id the id of a team.
     * @return the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public Team findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Team team = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                team = new Team();
                team.setId(set.getInt(Constants.ID));
                team.setName(set.getString(Constants.NAME));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao find by id operation is failed", e);
            throw new DAOException("TeamDao find by login operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return team;
    }


    /**
     * Retrieves a list of all team entries from the database.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Team>} - the list of all teams.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Team> getTeams(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<Team> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_TEAMS_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            Team team;
            while (set.next()) {
                team = new Team();
                team.setId(set.getInt(Constants.ID));
                team.setName(set.getString(Constants.NAME));
                list.add(team);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao get teams operation is failed", e);
            throw new DAOException("TeamDao get teams operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves amount of all teams from the database.
     *
     * @return amount of all teams.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfAllTeams() throws DAOException {
        int amount;
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(GET_AMOUNT_OF_ALL_TEAMS_QUERY))
        {
            set.next();
            amount = set.getInt(1);
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao get amount of teams operation is failed", e);
            throw new DAOException("TeamDao get amount of teams operation is failed", e);
        }
        return amount;
    }


    /**
     * Updates a team entry in the database.
     *
     * @param team the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(Team team) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY))
        {
            statement.setString(1, team.getName());
            statement.setInt(2, team.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao cannot create a team in DAO", e);
            throw new DAOException("TeamDao cannot create a team", e);
        }
    }


    /**
     * Deletes a team entry from the database.
     *
     * @param id the {@link by.academy.it.entity.Team} id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(int id) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao cannot delete a team in DAO", e);
            throw new DAOException("TeamDao cannot delete a team", e);
        }
    }

}
