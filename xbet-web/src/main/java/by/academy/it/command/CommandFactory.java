package by.academy.it.command;

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
    private static Map<String, Command> commands = new HashMap<>(70);

    /**
     * Constructs an instance of CommandFactory.
     */
    public CommandFactory() {

        commands.put(Constants.GET_LOCALE, new ChangeLocaleCommand());
        commands.put(Constants.GET_HOME, new ForwardRequestCommand(Constants.HOME));
        commands.put(Constants.GET_LOGIN, new ForwardRequestCommand(Constants.LOGIN));
        commands.put(Constants.POST_LOGIN, new LoginUserCommand());
        commands.put(Constants.POST_AUTHENTICATE, new AuthenticateLoginCommand());
        commands.put(Constants.GET_REGISTRATION, new ForwardRequestCommand(Constants.REGISTRATION));
        commands.put(Constants.POST_REGISTRATION, new CheckNewUserLoginCommand());
        commands.put(Constants.POST_REGISTER, new RegisterUserCommand());
        commands.put(Constants.GET_ERROR, new ForwardRequestCommand(Constants.ERROR));
        commands.put(Constants.GET_LOGOUT, new LogoutCommand());
        commands.put(Constants.NO_COMMAND, new NoActionCommand());
        commands.put(Constants.GET_MATCHES, new ShowUnplayedMatchesCommand());
        commands.put(Constants.POST_CHECK, new CheckIsUserLoggedInCommand());
        commands.put(Constants.POST_PLACE, new ShowPlaceBetPageCommand());
        commands.put(Constants.GET_BET, new ForwardRequestCommand(Constants.BET));
        commands.put(Constants.POST_BET, new ConfirmBetCommand());
        commands.put(Constants.POST_BALANCE, new CheckBalanceCommand());
        commands.put(Constants.POST_TOPUP, new TopUpBalanceCommand());
        commands.put(Constants.GET_BETS, new ShowUserBetsCommand());
        commands.put(Constants.POST_MATCHES, new FinishMatchCommand());
        commands.put(Constants.GET_GET_USERS, new ShowUsersCommand());
        commands.put(Constants.GET_CREATE_USER, new ForwardRequestCommand(Constants.CREATE_USER));
        commands.put(Constants.POST_CREATE_USER, new CreateUserCommand());
        commands.put(Constants.POST_UPDATE_USER, new ShowUpdateUserPageCommand());
        commands.put(Constants.POST_CHANGE_USER, new UpdateUserCommand());
        commands.put(Constants.POST_DELETE_USER, new DeleteUserCommand());
        commands.put(Constants.GET_UPDATE_USER, new ForwardRequestCommand(Constants.UPDATE_USER));
        commands.put(Constants.GET_GET_ROLES, new ShowRolesCommand());
        commands.put(Constants.GET_CREATE_ROLE, new ForwardRequestCommand(Constants.CREATE_ROLE));
        commands.put(Constants.POST_CREATE_ROLE, new CreateRoleCommand());
        commands.put(Constants.POST_UPDATE_ROLE, new ShowUpdateRolePageCommand());
        commands.put(Constants.GET_UPDATE_ROLE, new ForwardRequestCommand(Constants.UPDATE_ROLE));
        commands.put(Constants.POST_CHANGE_ROLE, new UpdateRoleCommand());
        commands.put(Constants.POST_DELETE_ROLE, new DeleteRoleCommand());
        commands.put(Constants.GET_GET_BETS, new ShowBetsCommand());
        commands.put(Constants.GET_CREATE_BET, new ForwardRequestCommand(Constants.CREATE_BET));
        commands.put(Constants.POST_CREATE_BET, new CreateBetCommand());
        commands.put(Constants.POST_UPDATE_BET, new ShowUpdateBetPageCommand());
        commands.put(Constants.GET_UPDATE_BET, new ForwardRequestCommand(Constants.UPDATE_BET));
        commands.put(Constants.POST_CHANGE_BET, new UpdateBetCommand());
        commands.put(Constants.POST_DELETE_BET, new DeleteBetCommand());
        commands.put(Constants.GET_GET_MATCHES, new ShowMatchesCommand());
        commands.put(Constants.GET_CREATE_MATCH, new ForwardRequestCommand(Constants.CREATE_MATCH));
        commands.put(Constants.POST_CREATE_MATCH, new CreateMatchCommand());
        commands.put(Constants.POST_UPDATE_MATCH, new ShowUpdateMatchPageCommand());
        commands.put(Constants.GET_UPDATE_MATCH, new ForwardRequestCommand(Constants.UPDATE_MATCH));
        commands.put(Constants.POST_CHANGE_MATCH, new UpdateMatchCommand());
        commands.put(Constants.POST_DELETE_MATCH, new DeleteMatchCommand());
        commands.put(Constants.GET_GET_RESULTS, new ShowResultsCommand());
        commands.put(Constants.GET_CREATE_RESULT, new ForwardRequestCommand(Constants.CREATE_RESULT));
        commands.put(Constants.POST_CREATE_RESULT, new CreateResultCommand());
        commands.put(Constants.POST_UPDATE_RESULT, new ShowUpdateResultPageCommand());
        commands.put(Constants.GET_UPDATE_RESULT, new ForwardRequestCommand(Constants.UPDATE_RESULT));
        commands.put(Constants.POST_CHANGE_RESULT, new UpdateResultCommand());
        commands.put(Constants.POST_DELETE_RESULT, new DeleteResultCommand());
        commands.put(Constants.GET_GET_TEAMS, new ShowTeamsCommand());
        commands.put(Constants.GET_CREATE_TEAM, new ForwardRequestCommand(Constants.CREATE_TEAM));
        commands.put(Constants.POST_CREATE_TEAM, new CreateTeamCommand());
        commands.put(Constants.POST_UPDATE_TEAM, new ShowUpdateTeamPageCommand());
        commands.put(Constants.GET_UPDATE_TEAM, new ForwardRequestCommand(Constants.UPDATE_TEAM));
        commands.put(Constants.POST_CHANGE_TEAM, new UpdateTeamCommand());
        commands.put(Constants.POST_DELETE_TEAM, new DeleteTeamCommand());
        commands.put(Constants.GET_RESULTS, new ShowLastResultsCommand());

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

}
