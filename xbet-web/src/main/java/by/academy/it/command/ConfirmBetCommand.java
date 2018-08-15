package by.academy.it.command;

import by.academy.it.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends {@link by.academy.it.command.Command} class, retrieves a match id, a bet and amount of money
 * and creates a bet entry.
 *
 */
public class ConfirmBetCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmBetCommand.class);
    private UserService userService = UserService.getInstance();

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
        userService.confirmBet(request, response);
    }
}
