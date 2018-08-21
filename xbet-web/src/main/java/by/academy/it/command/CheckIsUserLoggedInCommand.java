package by.academy.it.command;

import by.academy.it.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, checks if the user is logged in through ajax.
 *
 */
public class CheckIsUserLoggedInCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CheckIsUserLoggedInCommand.class);
    private UserService userService = UserService.getInstance();

    /**
     * Delegates operation to {@link by.academy.it.service.UserService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("check if the user logged in");
        userService.checkIsUserLoggedIn(request, response);
    }

}
