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
    private static CommandFactory instance = null;
    private static Map<String, Command> commands;

    /**
     * Prohibits creating an instance of class outside the class. Initializes the commands/URI map.
     */
    private CommandFactory() {

        commands = new HashMap<>();

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
        commands.put(Constants.GET_USERS, new ShowUsersCommand());
        commands.put(Constants.GET_CREATE_USER, new ForwardRequestCommand(Constants.CREATE_USER));
        commands.put(Constants.POST_CREATE_USER, new CreateUserCommand());
        commands.put(Constants.POST_UPDATE_USER, new ShowUpdateUserPageCommand());
        commands.put(Constants.POST_CHANGE_USER, new UpdateUserCommand());
        commands.put(Constants.POST_DELETE_USER, new DeleteUserCommand());
        commands.put(Constants.GET_UPDATE_USER, new ForwardRequestCommand(Constants.UPDATE_USER));
        commands.put(Constants.GET_ROLES, new ShowRolesCommand());
        commands.put(Constants.GET_CREATE_ROLE, new ForwardRequestCommand(Constants.CREATE_ROLE));
        commands.put(Constants.POST_CREATE_ROLE, new CreateRoleCommand());
        commands.put(Constants.POST_UPDATE_ROLE, new ShowUpdateRolePageCommand());
        commands.put(Constants.GET_UPDATE_ROLE, new ForwardRequestCommand(Constants.UPDATE_ROLE));
        commands.put(Constants.POST_CHANGE_ROLE, new UpdateRoleCommand());
        commands.put(Constants.POST_DELETE_ROLE, new DeleteRoleCommand());

    }


    /**
     * Creates {@code CommandFactory} instance if it is not created and returns it.
     *
     * @return {@code CommandFactory} instance.
     */
    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
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
