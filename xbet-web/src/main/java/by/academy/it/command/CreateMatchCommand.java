package by.academy.it.command;

import by.academy.it.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, creates a match.
 *
 */
public class CreateMatchCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateMatchCommand.class);
    private MatchService matchService;

    /**
     * Constructs an instance of the {@code CreateMatchCommand}.
     *
     * @param matchService {@link by.academy.it.service.MatchService}
     */
    CreateMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Delegates create match operation to {@link by.academy.it.service.MatchService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("create match operation");
        matchService.createMatch(request, response);
    }

}
