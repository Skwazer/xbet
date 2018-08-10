package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.UserDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class works with {@link by.academy.it.dao.UserDao} class.
 *
 */
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static UserService instance;

    /**
     * Prohibits creating instance of class outside the class.
     */
    private UserService() {
    }


    /**
     * Creates {@code UserService} instance if it is not created and returns it.
     *
     * @return {@code UserService} instance.
     */
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
            logger.info("UserService instance has been created");
        }
        return instance;
    }


    /**
     * Checks if a user with such login exists in the database.
     *
     * @param login the user's login.
     * @return {@code true} if a user exists or {@code false} otherwise.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public boolean isExistingLogin(String login) throws ServiceException {
        try {
            User user = userDao.findByLogin(login);
            if (user != null) {
                return true;
            }
        } catch (DAOException e) {
            logger.error("UserService cannot find a user by login", e);
            throw new ServiceException("UserService cannot find a user by login");
        }
        return false;
    }


    /**
     * Checks if a user with such login and password exists in the database.
     *
     * @param login the user's login.
     * @param password the user's password.
     * @return {@code true} if a user exists or {@code false} otherwise.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public boolean isPasswordCorrectForLogin(String login, String password) throws ServiceException {
        try {
            User user = userDao.findByLogin(login);
            if (user != null && password.equals(user.getPassword())) {
                return true;
            }
        } catch (DAOException e) {
            logger.error("UserService cannot check password", e);
            throw new ServiceException("UserService cannot check password");
        }
        return false;
    }


    /**
     * Creates a new user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public void createUser(User user) throws ServiceException {
        try {
            userDao.create(user);
        } catch (DAOException e) {
            logger.error("UserService cannot create a user", e);
            throw new ServiceException("UserService cannot create a user");
        }
    }


    /**
     * Updates user balance through {@link by.academy.it.dao.UserDao}.
     *
     * @param login the user's login.
     * @param amount money to add or remove.
     * @return the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public User updateUserBalance(String login, int amount) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
            if (user != null) {
                user.setBalance(user.getBalance() + amount);
                userDao.update(user);
            } else {
                logger.error("UserService cannot update user's balance");
                throw new ServiceException("UserService cannot update user's balance");
            }
        } catch (DAOException e) {
            logger.error("UserService cannot update a user", e);
            throw new ServiceException("UserService cannot update a user");
        }
        return user;
    }


    /**
     * Deletes a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param user the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public void deleteUser(User user) throws ServiceException {
        try {
            userDao.delete(user);
        } catch (DAOException e) {
            logger.error("UserService cannot delete a user", e);
            throw new ServiceException("UserService cannot delete a user");
        }
    }


    /**
     * Retrieves a user by login through {@link by.academy.it.dao.UserDao}.
     *
     * @param login the user's login.
     * @return the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public User findUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (DAOException e) {
            logger.error("UserService cannot find a user", e);
            throw new ServiceException("UserService cannot find a user");
        }
        return user;
    }


    /**
     * Retrieves a user by id through {@link by.academy.it.dao.UserDao}.
     *
     * @param id the user's id.
     * @return the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public User findUserById(int id) throws ServiceException {
        User user;
        try {
            user = userDao.findById(id);
        } catch (DAOException e) {
            logger.error("UserService cannot find a user", e);
            throw new ServiceException("UserService cannot find a user");
        }
        return user;
    }
}
