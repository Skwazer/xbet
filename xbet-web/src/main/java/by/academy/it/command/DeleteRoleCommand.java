package by.academy.it.command;

import by.academy.it.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, deletes a role.
 *
 */
public class DeleteRoleCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteRoleCommand.class);
    private RoleService roleService;

    /**
     * Constructs an instance of the {@code DeleteRoleCommand}.
     *
     * @param roleService {@link by.academy.it.service.RoleService}
     */
    DeleteRoleCommand(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Delegates delete role operation to {@link by.academy.it.service.RoleService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("delete role operation");
        roleService.deleteRole(request, response);
    }

}
