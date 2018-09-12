package by.academy.it.service;

import by.academy.it.dao.factory.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This factory creates and returns {@code Service} instances.
 *
 */
public class ServiceFactoryImpl implements ServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(ServiceFactoryImpl.class);
    private static DaoFactory daoFactory;

    private BetService betService;
    private TransactionalService transactionalService;
    private MatchService matchService;
    private static ModelService modelService;
    private ResultService resultService;
    private RoleService roleService;
    private TeamService teamService;
    private UserService userService;

    /**
     * Constructs an instance of ServiceFactory.
     *
     * @param factory a DaoFactory instance.
     */
    public ServiceFactoryImpl(DaoFactory factory) {
        daoFactory = factory;
        logger.info("ServiceFactory has been created");
    }


    /**
     * Creates {@code BetService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the BetService instance.
     *
     * @return {@link by.academy.it.service.BetService} instance.
     */
    public BetService getBetService() {
        if (betService == null) {
            betService = new BetServiceImpl(daoFactory.getBetDao());
        }
        return betService;
    }


    /**
     * Creates {@code TransactionalService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the TransactionalService instance.
     *
     * @return {@link TransactionalService} instance.
     */
    public TransactionalService getTransactionalService() {
        if (transactionalService == null) {
            transactionalService = new TransactionalServiceImpl
                    (daoFactory.getBetDao(), daoFactory.getTransactionalDao(), daoFactory.getUserDao());
        }
        return transactionalService;
    }


    /**
     * Creates {@code MatchService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the MatchService instance.
     *
     * @return {@link by.academy.it.service.MatchService} instance.
     */
    public MatchService getMatchService() {
        if (matchService == null) {
            matchService = new MatchServiceImpl(daoFactory.getMatchDao());
        }
        return matchService;
    }


    /**
     * Creates {@code ModelService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the ModelService instance.
     *
     * @return {@link by.academy.it.service.ModelService} instance.
     */
    static ModelService getModelService() {
        if (modelService == null) {
            modelService = new ModelServiceImpl(daoFactory.getTeamDao(), daoFactory.getMatchDao());
        }
        return modelService;
    }


    /**
     * Creates {@code ResultService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the ResultService instance.
     *
     * @return {@link by.academy.it.service.ResultService} instance.
     */
    public ResultService getResultService() {
        if (resultService == null) {
            resultService = new ResultServiceImpl(daoFactory.getResultDao());
        }
        return resultService;
    }


    /**
     * Creates {@code RoleService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the RoleService instance.
     *
     * @return {@link by.academy.it.service.RoleService} instance.
     */
    public RoleService getRoleService() {
        if (roleService == null) {
            roleService = new RoleServiceImpl(daoFactory.getRoleDao());
        }
        return roleService;
    }


    /**
     * Creates {@code TeamService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the TeamService instance.
     *
     * @return {@link by.academy.it.service.TeamService} instance.
     */
    public TeamService getTeamService() {
        if (teamService == null) {
            teamService = new TeamServiceImpl(daoFactory.getTeamDao());
        }
        return teamService;
    }


    /**
     * Creates {@code UserService} instance if it is not created.
     * Passes a DAO as a constructor parameter and returns the UserService instance.
     *
     * @return {@link by.academy.it.service.UserService} instance.
     */
    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl(daoFactory.getUserDao());
        }
        return userService;
    }

}
