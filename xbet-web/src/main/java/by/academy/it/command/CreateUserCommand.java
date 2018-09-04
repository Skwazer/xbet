package by.academy.it.command;

import by.academy.it.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, creates a user.
 *
 */
public class CreateUserCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserCommand.class);
    private UserService userService;

    /**
     * Constructs an instance of the {@code CreateUserCommand}.
     *
     * @param userService {@link by.academy.it.service.UserService}
     */
    CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Delegates create user operation to {@link by.academy.it.service.UserService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("creating of user");
        userService.createUser(request, response);
    }

}
