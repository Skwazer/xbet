package by.academy.it.command;

import by.academy.it.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends {@link by.academy.it.command.Command} class, finds match and puts it in the session.
 *
 */
public class ShowPlaceBetPageCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ShowPlaceBetPageCommand.class);
    private MatchService matchService = MatchService.getInstance();

    /**
     * Delegates operation to {@link by.academy.it.service.MatchService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("show place bet page operation");
        matchService.placeBet(request, response);
    }

}
