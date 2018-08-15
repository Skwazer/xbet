package by.academy.it.dao;

import by.academy.it.entity.User;
import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.User} entity class and has access to the 'users' database table.
 *
 */
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private static ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.users " +
            "(login, password, first_name, last_name, email, balance, role_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BALANCE_QUERY = "UPDATE xbet.users SET balance = ? WHERE login = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE xbet.users SET login = ?, password = ?," +
            " first_name = ?, last_name = ?, email = ?, balance = ?, role_id = ? WHERE id = ?";
    private static final String GET_BY_LOGIN_QUERY = "SELECT * FROM xbet.users WHERE login = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.users WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.users WHERE id = ?";
    private static final String GET_USERS_QUERY = "SELECT * FROM xbet.users";


    /**
     * Constructs an instance of {@code UserDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public UserDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
    }


    /**
     * Creates a new user entry in the database.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(CREATE_QUERY);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setDouble(6, user.getBalance());
            statement.setInt(7, user.getRole());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot create a user in DAO", e);
            throw new DAOException("UserDao cannot create a user", e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }


    /**
     * Updates a user's entry in the database.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setDouble(6, user.getBalance());
            statement.setInt(7, user.getRole());
            statement.setInt(8, user.getId());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot update a user in DAO", e);
            throw new DAOException("UserDao cannot update a user", e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }


    /**
     * Updates a user's entry in the database.
     *
     * @param login the user login.
     * @param balance the user balance.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void updateBalance(String login, double balance) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_BALANCE_QUERY);
            statement.setDouble(1, balance);
            statement.setString(2, login);
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot update user balance in DAO", e);
            throw new DAOException("UserDao cannot update user balance", e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }


    /**
     * Retrieves a user entry by login from the database.
     *
     * @param login the login of a user.
     * @return  the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public User findByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        User user = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_BY_LOGIN_QUERY);
            statement.setString(1, login);
            set = statement.executeQuery();
            if (set.next()) {
                user = new User();
                user.setId(set.getInt(ID));
                user.setLogin(set.getString(LOGIN));
                user.setPassword(set.getString(PASSWORD));
                user.setFirstName(set.getString(FIRST_NAME));
                user.setLastName(set.getString(LAST_NAME));
                user.setEmail(set.getString(EMAIL));
                user.setBalance(set.getDouble(BALANCE));
                user.setRole(set.getInt(ROLE_ID));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao find by login operation is failed", e);
            throw new DAOException("UserDao find by login operation is failed", e);
        } finally {
            closeResultSet(set);
            closeStatement(statement);
            closeConnection(connection);
        }
        return user;
    }


    /**
     * Retrieves a user entry by id from the database.
     *
     * @param id the id of a user.
     * @return  the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public User findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        User user = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                user = new User();
                user.setId(set.getInt(ID));
                user.setLogin(set.getString(LOGIN));
                user.setPassword(set.getString(PASSWORD));
                user.setFirstName(set.getString(FIRST_NAME));
                user.setLastName(set.getString(LAST_NAME));
                user.setEmail(set.getString(EMAIL));
                user.setBalance(set.getDouble(BALANCE));
                user.setRole(set.getInt(ROLE_ID));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao find by login operation is failed", e);
            throw new DAOException("UserDao find by login operation is failed", e);
        } finally {
            closeResultSet(set);
            closeStatement(statement);
            closeConnection(connection);
        }
        return user;
    }


    /**
     * Deletes a user entry from the database.
     *
     * @param  id the user id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void delete(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot delete a user in DAO", e);
            throw new DAOException("UserDao cannot delete a user", e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }


    /**
     * Retrieves a list of user entries.
     *
     * @return  the {@code List<User>} - a list of users.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public List<User> getUsers() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<User> list = new ArrayList<>();
        User user = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_USERS_QUERY);
            set = statement.executeQuery();
            while (set.next()) {
                user = new User();
                user.setId(set.getInt(ID));
                user.setLogin(set.getString(LOGIN));
                user.setPassword(set.getString(PASSWORD));
                user.setFirstName(set.getString(FIRST_NAME));
                user.setLastName(set.getString(LAST_NAME));
                user.setEmail(set.getString(EMAIL));
                user.setBalance(set.getDouble(BALANCE));
                user.setRole(set.getInt(ROLE_ID));
                list.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao find users operation is failed", e);
            throw new DAOException("UserDao users login operation is failed", e);
        } finally {
            closeResultSet(set);
            closeStatement(statement);
            closeConnection(connection);
        }
        return list;
    }


}
