package by.academy.it.command;

import by.academy.it.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends {@link by.academy.it.command.Command} class, terminates users session. Deletes user's data from the session.
 *
 */
public class LogoutCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(LogoutCommand.class);
    private UserService userService = UserService.getInstance();

    /**
     * Delegates logout operation to {@link by.academy.it.service.UserService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("logout operation");
        userService.logout(request, response);
    }

}
