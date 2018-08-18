package by.academy.it.dao;

import by.academy.it.entity.User;

import java.util.List;

/**
 * Defines {@code UserDao} methods.
 */
public interface UserDao {

    /**
     * Creates a new user entry.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(User user) throws DAOException;


    /**
     * Updates a user's balance.
     *
     * @param login the user login.
     * @param balance the user balance.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void updateBalance(String login, double balance) throws DAOException;


    /**
     * Retrieves a user entry by login.
     *
     * @param login the login of a user.
     * @return  the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    User findByLogin(String login) throws DAOException;


    /**
     * Retrieves a user entry by id.
     *
     * @param id the id of a user.
     * @return  the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    User findById(int id) throws DAOException;


    /**
     * Deletes a user entry.
     *
     * @param  id the user id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(int id) throws DAOException;


    /**
     * Retrieves a list of user entries.
     *
     * @return  the {@code List<User>} - a list of users.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<User> getUsers() throws DAOException;


    /**
     * Updates a user's entry.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void update(User user) throws DAOException;

}
