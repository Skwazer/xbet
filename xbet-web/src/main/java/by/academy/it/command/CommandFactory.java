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

    private static CommandFactory instance = null;
    private static Map<String, Command> commands;

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);
    private static final String GET_LOCALE = "GET/locale";
    private static final String GET_HOME = "GET/home";
    private static final String HOME = "home";
    private static final String GET_LOGIN = "GET/login";
    private static final String LOGIN = "login";
    private static final String POST_LOGIN = "POST/login";
    private static final String POST_AUTHENTICATE = "POST/authenticate";
    private static final String GET_REGISTRATION = "GET/registration";
    private static final String REGISTRATION = "registration";
    private static final String POST_REGISTRATION = "POST/registration";
    private static final String POST_REGISTER = "POST/register";
    private static final String GET_ERROR = "GET/error";
    private static final String ERROR = "error";
    private static final String GET_LOGOUT = "GET/logout";
    private static final String NO_COMMAND = "noCommand";
    private static final String GET_MATCHES = "GET/matches";
    private static final String POST_CHECK = "POST/check";
    private static final String POST_PLACE = "POST/place";
    private static final String GET_BET = "GET/bet";
    private static final String BET = "bet";
    private static final String POST_BET = "POST/bet";
    private static final String POST_BALANCE = "POST/balance";
    private static final String POST_TOPUP = "POST/topup";
    private static final String GET_BETS = "GET/bets";
    private static final String POST_MATCHES = "POST/matches";
    private static final String GET_USERS = "GET/users";
    private static final String GET_CREATE_USER = "GET/create-user";
    private static final String CREATE_USER = "create-user";
    private static final String POST_CREATE_USER = "POST/create-user";
    private static final String POST_CHANGE_USER = "POST/change-user";
    private static final String POST_UPDATE_USER = "POST/update-user";
    private static final String GET_UPDATE_USER = "GET/update-user";
    private static final String GET_DELETE_USER = "GET/delete-user";
    private static final String UPDATE_USER = "update-user";


    /**
     * Prohibits creating an instance of class outside the class. Initializes the commands/URI map.
     */
    private CommandFactory() {

        commands = new HashMap<>();

        commands.put(GET_LOCALE, new ChangeLocaleCommand());
        commands.put(GET_HOME, new ForwardRequestCommand(HOME));
        commands.put(GET_LOGIN, new ForwardRequestCommand(LOGIN));
        commands.put(POST_LOGIN, new LoginUserCommand());
        commands.put(POST_AUTHENTICATE, new AuthenticateLoginCommand());
        commands.put(GET_REGISTRATION, new ForwardRequestCommand(REGISTRATION));
        commands.put(POST_REGISTRATION, new CheckNewUserLoginCommand());
        commands.put(POST_REGISTER, new RegisterUserCommand());
        commands.put(GET_ERROR, new ForwardRequestCommand(ERROR));
        commands.put(GET_LOGOUT, new LogoutCommand());
        commands.put(NO_COMMAND, new NoActionCommand());
        commands.put(GET_MATCHES, new ShowUnplayedMatchesCommand());
        commands.put(POST_CHECK, new CheckIsUserLoggedInCommand());
        commands.put(POST_PLACE, new ShowPlaceBetPageCommand());
        commands.put(GET_BET, new ForwardRequestCommand(BET));
        commands.put(POST_BET, new ConfirmBetCommand());
        commands.put(POST_BALANCE, new CheckBalanceCommand());
        commands.put(POST_TOPUP, new TopUpBalanceCommand());
        commands.put(GET_BETS, new ShowBetsCommand());
        commands.put(POST_MATCHES, new FinishMatchCommand());
        commands.put(GET_USERS, new ShowUsersCommand());
        commands.put(GET_CREATE_USER, new ForwardRequestCommand(CREATE_USER));
        commands.put(POST_CREATE_USER, new CreateUserCommand());
        commands.put(POST_UPDATE_USER, new ShowUpdateUserPageCommand());
        commands.put(POST_CHANGE_USER, new UpdateUserCommand());
        commands.put(GET_DELETE_USER, new DeleteUserCommand());
        commands.put(GET_UPDATE_USER, new ForwardRequestCommand(UPDATE_USER));

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
            return commands.get(NO_COMMAND);
        }
        return commands.get(commandKey);
    }

}
