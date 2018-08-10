package by.academy.it.dao.factory;

import by.academy.it.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This factory creates and returns {@code Dao} instances.
 *
 */
public class DaoFactory {
    private static DaoFactory instance;
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    private BetDao betDao;
    private MatchDao matchDao;
    private ResultDao resultDao;
    private RoleDao roleDao;
    private TeamDao teamDao;
    private UserDao userDao;


    /**
     * Prohibits creating instance of class outside the class.
     */
    private DaoFactory() {
    }


    /**
     * Creates {@code DaoFactory} instance if it is not created and returns it.
     *
     * @return {@code DaoFactory} instance.
     */
    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
            logger.info("DaoFactory instance has been created");
        }
        return instance;
    }


    /**
     * Creates {@code BetDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the Dao instance.
     *
     * @return {@link by.academy.it.dao.BetDao} instance.
     */
    public BetDao getBetDao() {
        if (betDao == null) {
            betDao = new BetDaoImpl(connectionPool);
            logger.info("DaoFactory created a BetDao");
        }
        return betDao;
    }


    /**
     * Creates {@code UserDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code UserDao} instance.
     *
     * @return {@link by.academy.it.dao.UserDao} instance.
     */
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl(connectionPool);
            logger.info("DaoFactory created a UserDao");
        }
        return userDao;
    }


    /**
     * Creates {@code RoleDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code RoleDao} instance.
     *
     * @return {@link by.academy.it.dao.RoleDao} instance.
     */
    public RoleDao getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDaoImpl(connectionPool);
            logger.info("DaoFactory created a RoleDao");
        }
        return roleDao;
    }


    /**
     * Creates {@code TeamDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code TeamDao} instance.
     *
     * @return {@link by.academy.it.dao.TeamDao} instance.
     */
    public TeamDao getTeamDao() {
        if (teamDao == null) {
            teamDao = new TeamDaoImpl(connectionPool);
            logger.info("DaoFactory created a TeamDao");
        }
        return teamDao;
    }


    /**
     * Creates {@code MatchDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code MatchDao} instance.
     *
     * @return {@link by.academy.it.dao.MatchDao} instance.
     */
    public MatchDao getMatchDao() {
        if (matchDao == null) {
            matchDao = new MatchDaoImpl(connectionPool);
            logger.info("DaoFactory created a MatchDao");
        }
        return matchDao;
    }


    /**
     * Creates {@code ResultDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code ResultDao} instance.
     *
     * @return {@link by.academy.it.dao.ResultDao} instance.
     */
    public ResultDao getResultDao() {
        if (resultDao == null) {
            resultDao = new ResultDaoImpl(connectionPool);
            logger.info("DaoFactory created a ResultDao");
        }
        return resultDao;
    }

}
