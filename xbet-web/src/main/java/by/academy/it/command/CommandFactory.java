package by.academy.it.command;

import by.academy.it.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory of commands. Determines which command should handle a request.
 *
 */
public class CommandFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);
    private static CommandFactory instance;
    private static final Map<String, Command> commands = new HashMap<>(70);
    private static ServiceFactory serviceFactory;

    /**
     * Prohibits creating an instance outside the class.
     */
    private CommandFactory() {

        BetService betService = serviceFactory.getBetService();
        TransactionalService transactionalService = serviceFactory.getTransactionalService();
        MatchService matchService = serviceFactory.getMatchService();
        ResultService resultService = serviceFactory.getResultService();
        RoleService roleService = serviceFactory.getRoleService();
        TeamService teamService = serviceFactory.getTeamService();
        UserService userService = serviceFactory.getUserService();

        commands.put(Constants.NO_COMMAND, new NoActionCommand());
        commands.put(Constants.GET_LOCALE, new ChangeLocaleCommand(userService));
        commands.put(Constants.GET_HOME, new ForwardRequestCommand(Constants.HOME));
        commands.put(Constants.GET_LOGIN, new ForwardRequestCommand(Constants.LOGIN));
        commands.put(Constants.POST_LOGIN, new LoginUserCommand(userService));
        commands.put(Constants.POST_AUTHENTICATE, new AuthenticateLoginCommand(userService));
        commands.put(Constants.GET_REGISTRATION, new ForwardRequestCommand(Constants.REGISTRATION));
        commands.put(Constants.POST_REGISTRATION, new CheckNewUserLoginCommand(userService));
        commands.put(Constants.POST_REGISTER, new RegisterUserCommand(userService));
        commands.put(Constants.GET_ERROR, new ForwardRequestCommand(Constants.ERROR));
        commands.put(Constants.GET_LOGOUT, new LogoutCommand(userService));
        commands.put(Constants.GET_MATCHES, new ShowUnplayedMatchesCommand(matchService));
        commands.put(Constants.POST_CHECK, new CheckIsUserLoggedInCommand(userService));
        commands.put(Constants.POST_PLACE, new ShowPlaceBetPageCommand(matchService));
        commands.put(Constants.GET_BET, new ForwardRequestCommand(Constants.BET));
        commands.put(Constants.POST_BET, new ConfirmBetCommand(transactionalService));
        commands.put(Constants.POST_BALANCE, new CheckBalanceCommand(userService));
        commands.put(Constants.POST_TOPUP, new TopUpBalanceCommand(userService));
        commands.put(Constants.GET_BETS, new ShowUserBetsCommand(betService));
        commands.put(Constants.POST_MATCHES, new FinishMatchCommand(transactionalService));
        commands.put(Constants.GET_GET_USERS, new ShowUsersCommand(userService));
        commands.put(Constants.GET_CREATE_USER, new ShowCreateUserPage(userService));
        commands.put(Constants.POST_CREATE_USER, new CreateUserCommand(userService));
        commands.put(Constants.POST_UPDATE_USER, new ShowUpdateUserPageCommand(userService));
        commands.put(Constants.POST_CHANGE_USER, new UpdateUserCommand(userService));
        commands.put(Constants.POST_DELETE_USER, new DeleteUserCommand(userService));
        commands.put(Constants.GET_UPDATE_USER, new ForwardRequestCommand(Constants.UPDATE_USER));
        commands.put(Constants.GET_GET_ROLES, new ShowRolesCommand(roleService));
        commands.put(Constants.GET_CREATE_ROLE, new ForwardRequestCommand(Constants.CREATE_ROLE));
        commands.put(Constants.POST_CREATE_ROLE, new CreateRoleCommand(roleService));
        commands.put(Constants.POST_UPDATE_ROLE, new ShowUpdateRolePageCommand(roleService));
        commands.put(Constants.GET_UPDATE_ROLE, new ForwardRequestCommand(Constants.UPDATE_ROLE));
        commands.put(Constants.POST_CHANGE_ROLE, new UpdateRoleCommand(roleService));
        commands.put(Constants.POST_DELETE_ROLE, new DeleteRoleCommand(roleService));
        commands.put(Constants.GET_GET_BETS, new ShowBetsCommand(betService));
        commands.put(Constants.GET_GET_MATCHES, new ShowMatchesCommand(matchService));
        commands.put(Constants.GET_CREATE_MATCH, new ShowCreateMatchPageCommand(matchService));
        commands.put(Constants.POST_CREATE_MATCH, new CreateMatchCommand(matchService));
        commands.put(Constants.POST_UPDATE_MATCH, new ShowUpdateMatchPageCommand(matchService));
        commands.put(Constants.GET_UPDATE_MATCH, new ForwardRequestCommand(Constants.UPDATE_MATCH));
        commands.put(Constants.POST_CHANGE_MATCH, new UpdateMatchCommand(matchService));
        commands.put(Constants.POST_DELETE_MATCH, new DeleteMatchCommand(matchService));
        commands.put(Constants.GET_GET_RESULTS, new ShowResultsCommand(resultService));
        commands.put(Constants.GET_CREATE_RESULT, new ShowCreateResultPageCommand(resultService));
        commands.put(Constants.POST_CREATE_RESULT, new CreateResultCommand(resultService));
        commands.put(Constants.POST_UPDATE_RESULT, new ShowUpdateResultPageCommand(resultService));
        commands.put(Constants.GET_UPDATE_RESULT, new ForwardRequestCommand(Constants.UPDATE_RESULT));
        commands.put(Constants.POST_CHANGE_RESULT, new UpdateResultCommand(resultService));
        commands.put(Constants.POST_DELETE_RESULT, new DeleteResultCommand(resultService));
        commands.put(Constants.GET_GET_TEAMS, new ShowTeamsCommand(teamService));
        commands.put(Constants.GET_CREATE_TEAM, new ForwardRequestCommand(Constants.CREATE_TEAM));
        commands.put(Constants.POST_CREATE_TEAM, new CreateTeamCommand(teamService));
        commands.put(Constants.POST_UPDATE_TEAM, new ShowUpdateTeamPageCommand(teamService));
        commands.put(Constants.GET_UPDATE_TEAM, new ForwardRequestCommand(Constants.UPDATE_TEAM));
        commands.put(Constants.POST_CHANGE_TEAM, new UpdateTeamCommand(teamService));
        commands.put(Constants.POST_DELETE_TEAM, new DeleteTeamCommand(teamService));
        commands.put(Constants.GET_RESULTS, new ShowLastResultsCommand(resultService));

        logger.info("Command factory has been created");
    }

    /**
     * Returns an appropriate command by the requested path.
     *
     * @param request {@code HttpServletRequest} request.
     * @return {@link by.academy.it.command.Command} instance.
     */
    public Command getCommand(HttpServletRequest request) {
        String commandKey = request.getMethod() + request.getPathInfo();
        logger.info("request key is " + commandKey);
        if (!commands.containsKey(commandKey)) {
            logger.warn("command not found");
            return commands.get(Constants.NO_COMMAND);
        }
        return commands.get(commandKey);
    }

    /**
     * Sets a value of the serviceFactory field.
     *
     * @param factory {@link by.academy.it.service.ServiceFactory}
     */
    public static void setServiceFactory(ServiceFactory factory) {
        serviceFactory = factory;
        logger.info("ServiceFactory has been set");
    }

    /**
     * Creates an instance of CommandFactory if it is not created and returns it.
     *
     * @return CommandFactory instance.
     */
    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

}
