package by.academy.it.command;

import by.academy.it.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, tops up the user's balance.
 *
 */
public class TopUpBalanceCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(TopUpBalanceCommand.class);
    private UserService userService = UserService.getInstance();

    /**
     * Delegates top up balance to {@link by.academy.it.service.UserService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("top up balance operation");
        userService.topup(request, response);
    }

}
