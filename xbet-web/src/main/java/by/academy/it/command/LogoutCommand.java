package by.academy.it.command;

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

    /**
     * Deletes user's data from the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(Constants.USER, null);
        logger.info("logout is successful");
        response.sendRedirect(request.getContextPath() + Constants.MAIN_HOME);
    }

}
