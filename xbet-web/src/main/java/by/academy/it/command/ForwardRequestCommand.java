package by.academy.it.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends {@link by.academy.it.command.Command}, shows jsp pages.
 *
 */
public class ForwardRequestCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ForwardRequestCommand.class);
    private String page;

    ForwardRequestCommand(String page) {
        this.page = page;
    }

    /**
     * Forwards a request and response to a selected jsp.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("requested page is '" + page + "'");
        request.getRequestDispatcher(Constants.PATH + page + Constants.JSP).forward(request, response);
    }

}
