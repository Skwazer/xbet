package by.academy.it.command;

import by.academy.it.service.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, retrieves a list of bets and sends it to 'bets page'.
 *
 */
public class ShowBetsCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ShowBetsCommand.class);
    private BetService betService;

    /**
     * Constructs an instance of the {@code ShowBetsCommand}.
     *
     * @param betService {@link by.academy.it.service.BetService}
     */
    ShowBetsCommand(BetService betService) {
        this.betService = betService;
    }

    /**
     * Delegates operation to {@link by.academy.it.service.BetService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("show all bets operation");
        betService.showAllBets(request, response);
    }

}
