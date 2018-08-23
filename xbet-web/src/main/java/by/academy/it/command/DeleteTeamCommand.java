package by.academy.it.command;

import by.academy.it.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, deletes a team.
 *
 */
public class DeleteTeamCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteTeamCommand.class);
    private TeamService teamService = TeamService.getInstance();

    /**
     * Delegates delete team operation to {@link by.academy.it.service.TeamService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("delete team operation");
        teamService.deleteTeam(request, response);
    }

}
