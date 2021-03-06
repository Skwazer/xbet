package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.TeamDao;
import by.academy.it.entity.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.TeamDao} class.
 *
 */
class TeamServiceImpl implements TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    private TeamDao teamDao;


    /**
     * Constructs an instance of the {@code TeamService}.
     *
     * @param teamDao a TeamDao instance.
     */
    TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
        logger.info("TeamService has been created");
    }


    /**
     * Retrieves a list of teams through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void showTeams(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                List<Team> list = teamDao.getTeams(startFrom);
                double pages = Math.ceil(teamDao.getAmountOfAllTeams() / 10d);
                if (!list.isEmpty()) {
                    logger.info("Teams have been found");
                    request.setAttribute(Constants.TEAMS_LIST, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    request.getRequestDispatcher(Constants.PATH + Constants.GET_TEAMS + Constants.JSP)
                            .forward(request, response);
                } else {
                    logger.warn("Teams have not been found");
                    request.setAttribute(Constants.TEAMS_MESSAGE, Constants.TEAMS_LIST_EMPTY);

                    request.getRequestDispatcher(Constants.PATH + Constants.GET_TEAMS + Constants.JSP)
                            .forward(request, response);
                }
            } catch (DAOException e) {
                logger.error("An exception occurred during get all teams operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TEAMS_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Creates a team entry through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(Constants.NAME);
        if (Utils.isStringValid(name) && isNameValid(name)) {
            Team team = new Team();
            team.setName(name);
            try {
                teamDao.create(team);
                logger.info("team has been created");

                request.getSession().setAttribute(Constants.TEAMS_MESSAGE, Constants.CREATE_TEAM_MESSAGE);
                response.sendRedirect( request.getContextPath() + Constants.MAIN + Constants.GET_TEAMS);

            } catch (DAOException e) {
                logger.error("TeamService cannot create a team", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TEAM_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Create team data are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TEAM_DATA_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Validates name value.
     *
     * @param name a value to validate.
     * @return true if name matches the regular expression or false otherwise.
     */
    private boolean isNameValid(String name) {
        return name.matches(Constants.TEAM_REGEX);
    }


    /**
     * Retrieves a team by id through {@link by.academy.it.dao.RoleDao} and sends forward to the 'update team' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showUpdateTeamPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String idParam = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(idParam)) {
            try {
                int id = Integer.parseInt(idParam);
                Team team = teamDao.findById(id);

                logger.info("team has been retrieved");
                request.setAttribute(Constants.UPDATED_TEAM, team);

                request.getRequestDispatcher(Constants.PATH + Constants.UPDATE_TEAM + Constants.JSP)
                        .forward(request, response);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during show update team page operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_TEAM_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Show update team page operation parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_BET_PARAMETER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates a team entry through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void updateTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter(Constants.ID);
        String name = request.getParameter(Constants.NAME);
        if (Utils.areStringsValid(idParam, name) && isNameValid(name)) {
            try {
                int id = Integer.parseInt(idParam);
                Team team = new Team();
                team.setId(id);
                team.setName(name);
                teamDao.update(team);

                logger.info("team has been updated");
                request.getSession().setAttribute(Constants.TEAMS_MESSAGE, Constants.UPDATE_TEAM_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_TEAMS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during update team operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_TEAM_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Update team parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Deletes a team entry through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void deleteTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(key)) {
            try {
                int id = Integer.parseInt(key);
                teamDao.delete(id);

                logger.info("team has been deleted");
                request.getSession().setAttribute(Constants.TEAMS_MESSAGE, Constants.DELETE_TEAM_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_TEAMS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during delete team operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_TEAM_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Delete team parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }

}
