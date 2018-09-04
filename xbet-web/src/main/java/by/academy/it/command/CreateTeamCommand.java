package by.academy.it.command;

import by.academy.it.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link by.academy.it.command.Command}, creates a team.
 *
 */
public class CreateTeamCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateTeamCommand.class);
    private TeamService teamService;

    /**
     * Constructs an instance of the {@code CreateTeamCommand}.
     *
     * @param teamService {@link by.academy.it.service.TeamService}
     */
    CreateTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Delegates create team operation to {@link by.academy.it.service.TeamService}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("create team operation");
        teamService.createTeam(request, response);
    }

}
