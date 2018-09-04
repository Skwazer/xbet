package by.academy.it.command;

import by.academy.it.service.FinishMatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, randomly finishes the match.
 *
 */
public class FinishMatchCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(FinishMatchCommand.class);
    private FinishMatchService finishMatchService;

    /**
     * Constructs an instance of the {@code FinishMatchCommand}.
     *
     * @param finishMatchService {@link by.academy.it.service.FinishMatchService}
     */
    FinishMatchCommand(FinishMatchService finishMatchService) {
        this.finishMatchService = finishMatchService;
    }

    /**
     * Delegates operation to {@link by.academy.it.service.FinishMatchService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("finish match operation");
        finishMatchService.finishMatch(request, response);
    }

}
