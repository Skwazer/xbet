package by.academy.it.command;

import by.academy.it.service.TransactionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, creates a result.
 *
 */
public class CreateResultCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateResultCommand.class);
    private TransactionalService transactionalService;

    /**
     * Constructs an instance of the {@code CreateResultCommand}.
     *
     * @param transactionalService {@link by.academy.it.service.TransactionalService}.
     */
    CreateResultCommand(TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    /**
     * Delegates create result operation to {@link by.academy.it.service.TransactionalService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("create result operation");
        transactionalService.createResult(request, response);
    }

}
