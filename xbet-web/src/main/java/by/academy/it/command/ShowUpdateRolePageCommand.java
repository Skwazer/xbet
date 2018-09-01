package by.academy.it.command;

import by.academy.it.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends {@link by.academy.it.command.Command}, sends update role page to user.
 *
 */
public class ShowUpdateRolePageCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ShowUpdateRolePageCommand.class);
    private RoleService roleService = serviceFactory.getRoleService();

    /**
     * Delegates update role operation to {@link by.academy.it.service.RoleService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("update role operation");
        roleService.showUpdateRolePage(request, response);
    }

}
