package by.academy.it.dao;

import by.academy.it.entity.User;

/**
 * Defines {@code UserDao} methods. Extends {@link by.academy.it.dao.Dao} interface.
 */
public interface UserDao extends Dao {

    /**
     * Creates a new user entry.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(User user) throws DAOException;


    /**
     * Updates a user's entry.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void update(User user) throws DAOException;


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
     * @param  user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(User user) throws DAOException;

}
