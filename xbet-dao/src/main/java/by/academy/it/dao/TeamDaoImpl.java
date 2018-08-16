package by.academy.it.dao;

import by.academy.it.entity.Team;
import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Works with a {@link by.academy.it.entity.Team} entity class and has access to the 'teams' database table.
 *
 */
public class TeamDaoImpl implements TeamDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);
    private static ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.teams (name) VALUES (?)";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.teams WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.teams WHERE name = ?";


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
            statement.execute();
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
        try{
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                team = new Team();
                team.setId(set.getInt(ID));
                team.setName(set.getString(NAME));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao find by id operation is failed", e);
            throw new DAOException("TeamDao find by login operation is failed", e);
        } finally {
            closeResultSet(set);
            closeStatement(statement);
            closeConnection(connection);
        }
        return team;
    }


    /**
     * Deletes a team entry from the database.
     *
     * @param team the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(Team team) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setString(1, team.getName());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("TeamDao cannot delete a role in DAO", e);
            throw new DAOException("TeamDao cannot delete a role", e);
        }
    }

}
