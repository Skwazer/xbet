package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.Role} entity class and has access to the 'roles' database table.
 *
 */
public class RoleDaoImpl implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    private ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.roles (role) VALUES (?)";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.roles WHERE id = ?";
    private static final String GET_ROLES_QUERY = "SELECT * FROM xbet.roles ORDER BY id";
    private static final String GET_ROLES_IDS_QUERY = "SELECT id FROM xbet.roles ORDER BY id";
    private static final String UPDATE_QUERY = "UPDATE xbet.roles SET role = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.roles WHERE id = ?";


    /**
     * Constructs an instance of {@code RoleDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public RoleDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
        logger.info("RoleDao has been created");
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
            connection.setReadOnly(true);
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
     * Retrieves a list of role entries from the database.
     *
     * @return a list of {@link by.academy.it.entity.Role} entities.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Role> getRoles() throws DAOException {
        List<Role> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.createStatement();
            set = statement.executeQuery(GET_ROLES_QUERY);
            Role role;
            while (set.next()) {
                role = new Role();
                role.setId(set.getInt(Constants.ID));
                role.setRole(set.getString(Constants.ROLE));
                list.add(role);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao get roles operation is failed", e);
            throw new DAOException("RoleDao get roles operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves a list role entries' ids from the database.
     *
     * @return a list of {@link by.academy.it.entity.Role} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Integer> getIds() throws DAOException {
        List<Integer> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.createStatement();
            set = statement.executeQuery(GET_ROLES_IDS_QUERY);
            while (set.next()) {
                list.add(set.getInt(Constants.ID));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao get roles ids operation is failed", e);
            throw new DAOException("RoleDao get roles ids operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Updates a role entry in the database.
     *
     * @param role the {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(Role role) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY))
        {
            statement.setString(1, role.getRole());
            statement.setInt(2, role.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao cannot update a role in DAO", e);
            throw new DAOException("RoleDao cannot update a role", e);
        }
    }


    /**
     * Deletes a role entry from the database.
     *
     * @param id - role id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(int id) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("RoleDao cannot delete a role in DAO", e);
            throw new DAOException("RoleDao cannot delete a role", e);
        }
    }


}
