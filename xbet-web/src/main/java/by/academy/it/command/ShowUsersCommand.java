package by.academy.it.command;

import by.academy.it.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, shows a list of users.
 *
 */
public class ShowUsersCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ShowUsersCommand.class);
    private UserService userService;

    /**
     * Constructs an instance of the {@code ShowUsersCommand}.
     *
     * @param userService {@link by.academy.it.service.UserService}
     */
    ShowUsersCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Delegates show users operation to {@link by.academy.it.service.UserService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("show users operation");
        userService.showUsers(request, response);
    }

}
