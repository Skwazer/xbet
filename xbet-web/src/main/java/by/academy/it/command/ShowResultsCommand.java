package by.academy.it.command;

import by.academy.it.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends {@link by.academy.it.command.Command}, shows a list of results.
 *
 */
public class ShowResultsCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ShowResultsCommand.class);
    private ResultService resultService = serviceFactory.getResultService();

    /**
     * Delegates show results operation to {@link by.academy.it.service.ResultService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("show results operation");
        resultService.showResults(request, response);
    }

}
