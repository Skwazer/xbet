package by.academy.it.command;

import by.academy.it.service.TransactionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, creates a random result of the match.
 *
 */
public class CreateRandomResultCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateRandomResultCommand.class);
    private TransactionalService transactionalService;

    /**
     * Constructs an instance of the {@code CreateRandomResultCommand}.
     *
     * @param transactionalService {@link by.academy.it.service.TransactionalService}.
     */
    CreateRandomResultCommand(TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    /**
     * Delegates operation to {@link by.academy.it.service.TransactionalService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("create random result operation");
        transactionalService.createRandomResult(request, response);
    }

}
