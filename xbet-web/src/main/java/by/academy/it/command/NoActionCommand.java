package by.academy.it.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This command is used when the application doesn't have a command for received URL.
 *
 */
public class NoActionCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(NoActionCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ERROR_MESSAGE, NO_COMMAND);
        logger.info("redirect to error page");

        response.sendRedirect(request.getContextPath() + ERROR);
    }
}
