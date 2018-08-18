package by.academy.it.dao;

import by.academy.it.entity.Role;
import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Works with a {@link by.academy.it.entity.Role} entity class and has access to the 'roles' database table.
 *
 */
public class RoleDaoImpl implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    private ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.roles (role) VALUES (?)";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.roles WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.roles WHERE role = ?";


    /**
     * Constructs an instance of {@code RoleDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public RoleDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
    }


    /**
     * Creates a new role entry in the database.
     *
     * @param role the {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(Role role) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY))
        {
            statement.setString(1, role.getRole());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao cannot create a role in DAO", e);
            throw new DAOException("RoleDao cannot create a role", e);
        }
    }


    /**
     * Retrieves a role entry by id from the database.
     *
     * @param id the id of a role.
     * @return {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public Role findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Role role = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                role = new Role();
                role.setId(set.getInt(Constants.ID));
                role.setRole(set.getString(Constants.ROLE));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao find by login operation is failed", e);
            throw new DAOException("RoleDao find by id operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return role;
    }


    /**
     * Deletes a role entry from the database.
     *
     * @param role the {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(Role role) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setString(1, role.getRole());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao cannot delete a role in DAO", e);
            throw new DAOException("RoleDao cannot delete a role", e);
        }
    }
}
