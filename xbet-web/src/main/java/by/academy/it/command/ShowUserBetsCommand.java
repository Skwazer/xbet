package by.academy.it.command;

import by.academy.it.service.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, retrieves a list of the user's bets and sends it to 'bets page'.
 *
 */
public class ShowUserBetsCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ShowUserBetsCommand.class);
    private BetService betService;

    /**
     * Constructs an instance of the {@code ShowUserBetsCommand}.
     *
     * @param betService {@link by.academy.it.service.BetService}
     */
    ShowUserBetsCommand(BetService betService) {
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
        logger.info("show user bets operation");
        betService.showUserBets(request, response);
    }

}
