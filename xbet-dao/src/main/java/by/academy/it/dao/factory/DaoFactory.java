package by.academy.it.dao.factory;

import by.academy.it.dao.*;

/**
 * DaoFactory interface, defines factory methods.
 */
public interface DaoFactory {

    /**
     * Creates {@code BetDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the BetDao instance.
     *
     * @return {@link by.academy.it.dao.BetDao} instance.
     */
    BetDao getBetDao();


    /**
     * Creates {@code UserDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code UserDao} instance.
     *
     * @return {@link by.academy.it.dao.UserDao} instance.
     */
    UserDao getUserDao();


    /**
     * Creates {@code RoleDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code RoleDao} instance.
     *
     * @return {@link by.academy.it.dao.RoleDao} instance.
     */
    RoleDao getRoleDao();


    /**
     * Creates {@code TeamDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code TeamDao} instance.
     *
     * @return {@link by.academy.it.dao.TeamDao} instance.
     */
    TeamDao getTeamDao();


    /**
     * Creates {@code MatchDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code MatchDao} instance.
     *
     * @return {@link by.academy.it.dao.MatchDao} instance.
     */
    MatchDao getMatchDao();


    /**
     * Creates {@code ResultDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code ResultDao} instance.
     *
     * @return {@link by.academy.it.dao.ResultDao} instance.
     */
    ResultDao getResultDao();


    /**
     * Creates {@code TransactionalDao} instance if it is not created.
     * Passes a connectionPool as a constructor parameter and returns the {@code TransactionalDao} instance.
     *
     * @return {@link by.academy.it.dao.TransactionalDao} instance.
     */
    TransactionalDao getTransactionalDao();

}
