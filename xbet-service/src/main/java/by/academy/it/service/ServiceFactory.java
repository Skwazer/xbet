package by.academy.it.service;

/**
 * ServiceFactory interface, defines factory methods.
 */
public interface ServiceFactory {

    /**
     * Creates {@code BetService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the BetService instance.
     *
     * @return {@link by.academy.it.service.BetService} instance.
     */
    BetService getBetService();


    /**
     * Creates {@code TransactionalService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the TransactionalService instance.
     *
     * @return {@link TransactionalService} instance.
     */
    TransactionalService getTransactionalService();


    /**
     * Creates {@code MatchService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the MatchService instance.
     *
     * @return {@link by.academy.it.service.MatchService} instance.
     */
    MatchService getMatchService();


    /**
     * Creates {@code ResultService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the ResultService instance.
     *
     * @return {@link by.academy.it.service.ResultService} instance.
     */
    ResultService getResultService();


    /**
     * Creates {@code RoleService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the RoleService instance.
     *
     * @return {@link by.academy.it.service.RoleService} instance.
     */
    RoleService getRoleService();


    /**
     * Creates {@code TeamService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the TeamService instance.
     *
     * @return {@link by.academy.it.service.TeamService} instance.
     */
    TeamService getTeamService();


    /**
     * Creates {@code UserService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the UserService instance.
     *
     * @return {@link by.academy.it.service.UserService} instance.
     */
    UserService getUserService();

}
