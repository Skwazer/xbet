package by.academy.it.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This command terminates users session, deletes user's data from the session.
 *
 */
public class LogoutCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(USER, null);
        logger.info("logout is successful");

        response.sendRedirect(getReferrerURL(request));
    }
}
