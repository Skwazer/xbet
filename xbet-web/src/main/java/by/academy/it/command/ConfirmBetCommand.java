package by.academy.it.command;

import by.academy.it.service.TransactionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, retrieves a match id, a bet and amount of money
 * and creates a bet entry.
 *
 */
public class ConfirmBetCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmBetCommand.class);
    private TransactionalService transactionalService;

    /**
     * Constructs an instance of the {@code ConfirmBetCommand}.
     *
     * @param transactionalService {@link by.academy.it.service.TransactionalService}
     */
    ConfirmBetCommand(TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    /**
     * Delegates confirm bet operation to {@link by.academy.it.service.UserService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("confirm bet operation");
        transactionalService.confirmBet(request, response);
    }

}
