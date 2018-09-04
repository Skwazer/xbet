package by.academy.it.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, used when the application doesn't have a command for received URL.
 *
 */
public class NoActionCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(NoActionCommand.class);

    /**
     * Sends redirect to error page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NO_COMMAND);
        logger.info("redirect to error page");
        response.sendRedirect(request.getContextPath() + Constants.MAIN_ERROR);
    }

}
