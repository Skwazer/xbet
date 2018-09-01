package by.academy.it.dao.factory;

import by.academy.it.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This factory creates and returns {@code Dao} instances.
 *
 */
public class DaoFactoryImpl implements DaoFactory {

    private ConnectionPool connectionPool;
    private static final Logger logger = LoggerFactory.getLogger(DaoFactoryImpl.class);

    private BetDao betDao;
    private MatchDao matchDao;
    private ResultDao resultDao;
    private RoleDao roleDao;
    private TeamDao teamDao;
    private UserDao userDao;
    private TransactionalDao transactionalDao;


    /**
     * Constructs an instance of DaoFactory.
     */
    public DaoFactoryImpl(ConnectionPool pool) {
        connectionPool = pool;
        logger.info("DaoFactory has been created");
    }


    /**
     * Creates {@code BetDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the BetDao instance.
     *
     * @return {@link by.academy.it.dao.BetDao} instance.
     */
    public BetDao getBetDao() {
        if (betDao == null) {
            betDao = new BetDaoImpl(connectionPool);
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
        }
        return resultDao;
    }


    /**
     * Creates {@code TransactionalDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code TransactionalDao} instance.
     *
     * @return {@link by.academy.it.dao.TransactionalDao} instance.
     */
    public TransactionalDao getTransactionalDao() {
        if (transactionalDao == null) {
            transactionalDao = new TransactionalDaoImpl(connectionPool);
        }
        return transactionalDao;
    }

}
