package by.academy.it.command;

import by.academy.it.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, deletes a result.
 *
 */
public class DeleteResultCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteResultCommand.class);
    private ResultService resultService;

    /**
     * Constructs an instance of the {@code DeleteResultCommand}.
     *
     * @param resultService {@link by.academy.it.service.ResultService}
     */
    DeleteResultCommand(ResultService resultService) {
        this.resultService = resultService;
    }

    /**
     * Delegates delete result operation to {@link by.academy.it.service.ResultService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("delete result operation");
        resultService.deleteResult(request, response);
    }

}
