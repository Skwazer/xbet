package by.academy.it.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This command checks if the user's data is located in the session through ajax.
 *
 */
public class CheckIsUserLoggedInCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(CheckIsUserLoggedInCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER) != null){
            logger.info("user is logged in");
            response.setHeader(RESULT, SUCCESS);
        } else {
            response.setHeader(RESULT, FAILURE);
            logger.info("user is not logged in");
        }
        request.getRequestDispatcher(getReferrerPath(request)).forward(request, response);
    }
}
