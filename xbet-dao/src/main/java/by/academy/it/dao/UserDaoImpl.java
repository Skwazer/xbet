package by.academy.it.dao;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with a {@link by.academy.it.entity.User} entity class and has access to the 'users' database table.
 *
 */
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private ConnectionPool pool;

    private static final String CREATE_QUERY = "INSERT INTO xbet.users " +
            "(login, password, first_name, last_name, email, balance, role_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BALANCE_QUERY = "UPDATE xbet.users SET balance = ? WHERE login = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE xbet.users SET login = ?, " +
            "first_name = ?, last_name = ?, email = ?, balance = ?, role_id = ? WHERE id = ?";
    private static final String GET_BY_LOGIN_QUERY = "SELECT * FROM xbet.users WHERE login = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM xbet.users WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM xbet.users WHERE id = ?";
    private static final String GET_USERS_QUERY = "SELECT * FROM xbet.users limit ?, 10";
    private static final String GET_AMOUNT_USERS_QUERY = "SELECT COUNT(*) FROM xbet.users";


    /**
     * Constructs an instance of {@code UserDaoImpl} with the specified connection pool.
     *
     * @param connectionPool the connection pool.
     */
    public UserDaoImpl(ConnectionPool connectionPool) {
        pool = connectionPool;
        logger.info("UserDao has been created");
    }


    /**
     * Creates a new user entry in the database.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void create(User user) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY))
        {
            statement.setString(1, user.getLogin());
            statement.setString(2, String.valueOf(user.getPassword()));
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setDouble(6, user.getBalance());
            statement.setInt(7, user.getRole());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot create a user in DAO", e);
            throw new DAOException("UserDao cannot create a user", e);
        }
    }


    /**
     * Updates a user's entry in the database.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(User user) throws DAOException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY))
        {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setDouble(5, user.getBalance());
            statement.setInt(6, user.getRole());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot updateStatus a user in DAO", e);
            throw new DAOException("UserDao cannot updateStatus a user", e);
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
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BALANCE_QUERY))
        {
            statement.setDouble(1, balance);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot updateStatus user balance in DAO", e);
            throw new DAOException("UserDao cannot updateStatus user balance", e);
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
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_BY_LOGIN_QUERY);
            statement.setString(1, login);
            set = statement.executeQuery();
            if (set.next()) {
                user = setUserFields(set);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao find by login operation is failed", e);
            throw new DAOException("UserDao find by login operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return user;
    }


    /**
     * Returns user entity with fields values retrieved from the database.
     *
     * @param set java.sql.ResultSet
     * @return {@link by.academy.it.entity.User} entity.
     * @throws SQLException if an exception occurred during the operation.
     */
    private User setUserFields(ResultSet set) throws SQLException {
        User user = new User();
        user.setId(set.getInt(Constants.ID));
        user.setLogin(set.getString(Constants.LOGIN));
        user.setPassword(set.getString(Constants.PASSWORD).toCharArray());
        user.setFirstName(set.getString(Constants.FIRST_NAME));
        user.setLastName(set.getString(Constants.LAST_NAME));
        user.setEmail(set.getString(Constants.EMAIL));
        user.setBalance(set.getDouble(Constants.BALANCE));
        user.setRole(set.getInt(Constants.ROLE_ID));
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
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                user = setUserFields(set);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao find by login operation is failed", e);
            throw new DAOException("UserDao find by login operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
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
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao cannot delete a user in DAO", e);
            throw new DAOException("UserDao cannot delete a user", e);
        }
    }


    /**
     * Retrieves a list of user entries.
     *
     * @param startFrom position from which the select operation is performed.
     * @return  the {@code List<User>} - a list of users.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    @Override
    public List<User> getUsers(int startFrom) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        List<User> list = new ArrayList<>(10);
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.prepareStatement(GET_USERS_QUERY);
            statement.setInt(1, startFrom);
            set = statement.executeQuery();
            while (set.next()) {
                list.add(setUserFields(set));
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao find users operation is failed", e);
            throw new DAOException("UserDao users login operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return list;
    }


    /**
     * Retrieves amount of users from the database.
     *
     * @return amount of users.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public int getAmountOfUsers() throws DAOException {
        int amount = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            connection.setReadOnly(true);
            statement = connection.createStatement();
            set = statement.executeQuery(GET_AMOUNT_USERS_QUERY);
            if (set.next()) {
                amount = set.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("UserDao get amount of users operation is failed", e);
            throw new DAOException("UserDao get amount of users operation is failed", e);
        } finally {
            Utils.closeResultSet(set);
            Utils.closeStatement(statement);
            Utils.closeConnection(connection);
        }
        return amount;
    }

}
