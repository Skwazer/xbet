package by.academy.it.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is a root class for commands, contains constants and useful methods.
 */
public abstract class Command {

    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    public static final String USER = "user";
    public static final String CURRENT_URI = "currentURI";
    public static final String MAIN = "/main/";
    public static final String PATH = "/WEB-INF/jsp/";
    public static final String JSP = ".jsp";
    public static final String RESULT = "result";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String ERROR = "/main/error";
    public static final String MATCH_EXCEPTION = "match.exception";
    public static final String LANG = "lang";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String HOME = "/main/home";
    public static final String LOGIN_FAILURE = "login.failure";
    public static final String MESSAGE = "message";
    public static final String INCORRECT_PASSWORD = "incorrectPassword";
    public static final String LOGIN_EXCEPTION = "login.exception";
    public static final String NO_COMMAND = "noCommand";
    public static final String MATCH = "match";
    public static final String MATCH_ID = "matchId";
    public static final String MATCH_ID_ERROR = "match.id.error";
    public static final String MAIN_BET = "/main/bet";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String REGISTRATION_ERROR = "registration.error";
    public static final String MATCHES = "matches";
    public static final String MATCHES_LIST = "matchesList";
    public static final String BET = "bet";
    public static final String AMOUNT = "amount";
    public static final String BET_PARAM_ERROR = "bet.param.error";
    public static final String BET_ERROR = "bet.error";
    public static final String BALANCE_ERROR = "balance.error";
    public static final String NOT_ENOUGH = "NOT_ENOUGH";
    public static final String KEY = "key";
    public static final String AMOUNT_ERROR = "amount.error";
    public static final String TOPUP_ERROR = "top.up.error";
    public static final String CONFIRM_MESSAGE = "confirmMessage";
    public static final String BETS_EXCEPTION = "bets.error";
    public static final String BETS = "bets";
    public static final String FINISH_ERROR = "finish.error";
    public static final String FIRST_WON = "1";
    public static final String SECOND_WON = "2";
    public static final String DRAW = "X";
    public static final String NUMBER_ERROR = "number.error";



    /**
     * Abstract method. Commands must provide an implementation.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


    /**
     * creates the referrer uri from a request.
     *
     * @param request {@code httpservletrequest} request.
     * @return referrer uri.
     */
    String getReferrerURI(HttpServletRequest request) {
        String URI = request.getSession().getAttribute(CURRENT_URI).toString();
        URI = URI.replaceFirst(request.getContextPath(), "");
        URI = URI.replaceFirst(PATH, "");
        URI = URI.replaceFirst(JSP, "");
        URI = request.getContextPath() + MAIN + URI;
        logger.info("referrer URI - " + URI);

        return URI;
    }


    /**
     * Creates the referrer path from a request.
     *
     * @param request {@code HttpServletRequest} request.
     * @return referrer path.
     */
    String getReferrerPath(HttpServletRequest request) {
        String URI = request.getSession().getAttribute(CURRENT_URI).toString();
        String path = URI.replaceFirst(request.getContextPath(), "");
        logger.info("referrer path: " + path);

        return path;
    }


    /**
     * Checks if a string is valid.
     *
     * @param string a string to check.
     * @return {@code true} if a string is neither null nor empty, {@code false} otherwise.
     */
    boolean isValidString(String string) {
        return string != null && !string.trim().isEmpty();
    }
}
