package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.MatchDao}.
 *
 */
class MatchServiceImpl implements MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);
    private MatchDao matchDao;

    /**
     * Constructs an instance of the {@code MatchService}.
     *
     * @param matchDao a MatchDao instance.
     */
    MatchServiceImpl(MatchDao matchDao) {
        this.matchDao = matchDao;
        logger.info("MatchService has been created");
    }


    /**
     * Retrieves a match id, finds match, puts it in the session and sends to user 'place bet' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void placeBet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String matchId = request.getParameter(Constants.MATCH_ID);
        if (Utils.isStringValid(matchId)) {
            try {
                int id = Integer.parseInt(matchId);
                Match match = matchDao.findById(id);
                if (match == null) {
                    logger.error("Cannot find a match with such id");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCH_NOT_FOUND_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                logger.info("match has been retrieved - [" + match.getId() + "]");
                ServiceFactoryImpl.getModelService().setTeams(match);
                request.getSession().setAttribute(Constants.MATCH, match);

                response.sendRedirect(request.getContextPath() + Constants.MAIN_BET);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during get match operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCH_EXCEPTION);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Match id is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCH_ID_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a list of unplayed matches, puts it in the session and sends to 'matches' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showUnplayedMatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                List<Match> list = matchDao.getUnplayedMatches(startFrom);
                double pages = Math.ceil(matchDao.getAmountOfUnplayedMatches() / 10d);
                if (!list.isEmpty()) {
                    logger.info("Matches have been found");

                    for (Match match : list) {
                        ServiceFactoryImpl.getModelService().setTeams(match);
                    }
                    request.setAttribute(Constants.MATCHES_LIST, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    request.getRequestDispatcher(Constants.PATH + Constants.MATCHES + Constants.JSP)
                            .forward(request, response);
                } else {
                    logger.warn("Matches have not been found");

                    request.setAttribute(Constants.MATCHES_MESSAGE, Constants.MATCHES_LIST_EMPTY);
                    request.getRequestDispatcher(Constants.PATH + Constants.MATCHES + Constants.JSP)
                            .forward(request, response);
                }
            } catch (DAOException e) {
                logger.error("An exception occurred during get unplayed matches operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCH_EXCEPTION);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Retrieves a list of unplayed matches, puts it in the session and sends to 'matches' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showMatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                List<Match> list = matchDao.getMatches(startFrom);
                double pages = Math.ceil(matchDao.getAmountOfAllMatches() / 10d);
                if (!list.isEmpty()) {
                    logger.info("Matches have been found");
                    request.setAttribute(Constants.MATCHES_LIST, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    request.getRequestDispatcher(Constants.PATH + Constants.GET_MATCHES + Constants.JSP)
                            .forward(request, response);
                } else {
                    logger.warn("Matches have not been found");
                    request.setAttribute(Constants.MATCHES_MESSAGE, Constants.MATCHES_LIST_EMPTY);

                    request.getRequestDispatcher(Constants.PATH + Constants.GET_MATCHES + Constants.JSP)
                            .forward(request, response);
                }
            } catch (DAOException e) {
                logger.error("An exception occurred during get all matches operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCHES_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Creates a match through {@link by.academy.it.dao.MatchDao}
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dateParam = request.getParameter(Constants.DATE);
        String team1IDParam = request.getParameter(Constants.TEAM1_ID);
        String team2IDParam = request.getParameter(Constants.TEAM2_ID);
        String v1 = request.getParameter(Constants.V1);
        String X = request.getParameter(Constants.X);
        String v2 = request.getParameter(Constants.V2);
        String X1 = request.getParameter(Constants.X1);
        String v12 = request.getParameter(Constants.V12);
        String X2 = request.getParameter(Constants.X2);

        if (Utils.areStringsValid(dateParam, team1IDParam, team2IDParam, v1, X, v2, X1, v12, X2)) {
            try {
                Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateParam);
                java.sql.Date date = new java.sql.Date(utilDate.getTime());
                int team1_id = Integer.parseInt(team1IDParam);
                int team2_id = Integer.parseInt(team2IDParam);
                double victory1 = Double.parseDouble(v1);
                double draw = Double.parseDouble(X);
                double victory2 = Double.parseDouble(v2);
                double victory1orDraw = Double.parseDouble(X1);
                double victory1or2 = Double.parseDouble(v12);
                double victory2orDraw = Double.parseDouble(X2);
                if (!checkCoefficientsValues(victory1, draw, victory2, victory1orDraw, victory1or2, victory2orDraw)) {
                    logger.warn("Coefficients values are not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.COEF_VALUE_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                Match match = new Match();
                match.setDate(date);
                match.setTeam1_id(team1_id);
                match.setTeam2_id(team2_id);
                match.setVictory1(victory1);
                match.setDraw(draw);
                match.setVictory2(victory2);
                match.setVictory1OrDraw(victory1orDraw);
                match.setVictory1Or2(victory1or2);
                match.setVictory2OrDraw(victory2orDraw);
                matchDao.create(match);

                logger.info("match has been created");
                request.getSession().setAttribute(Constants.MATCHES_MESSAGE, Constants.CREATE_MATCH_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_MATCHES);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (ParseException e) {
                logger.error("Cannot parse a date parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DATE_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during create match operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_MATCH_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Create match parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a match by id through {@link by.academy.it.dao.MatchDao} and sends forward to 'update match' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showUpdateMatchPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(key)) {
            try {
                int id = Integer.parseInt(key);
                Match match = matchDao.findById(id);
                logger.info("match has been retrieved");
                request.setAttribute(Constants.UPDATED_MATCH, match);

                List<Integer> teamsIds = ServiceFactoryImpl.getIdService().getTeamsIds();
                if (teamsIds != null) {
                    request.setAttribute(Constants.TEAMS_IDS, teamsIds);
                    logger.info("team ids have been retrieved");
                } else {
                    logger.warn("Teams ids have not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TEAMS_IDS_NOT_FOUND);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                request.getRequestDispatcher(Constants.PATH + Constants.UPDATE_MATCH + Constants.JSP)
                        .forward(request, response);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during show update match page operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_MATCH_ERROR);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Show update match page operation parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a list of teams' ids and sends forward to 'create match' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showCreateMatchPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            List<Integer> teamsIds = ServiceFactoryImpl.getIdService().getTeamsIds();
            if (teamsIds != null) {
                request.setAttribute(Constants.TEAMS_IDS, teamsIds);
                logger.info("teams ids have been retrieved");
            } else {
                logger.warn("Team ids have not been found");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TEAMS_IDS_NOT_FOUND);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
                return;
            }
            request.getRequestDispatcher(Constants.PATH + Constants.CREATE_MATCH + Constants.JSP)
                    .forward(request, response);

        }catch (DAOException e) {
            logger.error("An exception occurred during show update match page operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_MATCH_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates a match entry through {@link by.academy.it.dao.MatchDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void updateMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter(Constants.ID);
        String dateParam = request.getParameter(Constants.DATE);
        String team1IDParam = request.getParameter(Constants.TEAM1_ID);
        String team2IDParam = request.getParameter(Constants.TEAM2_ID);
        String v1 = request.getParameter(Constants.V1);
        String X = request.getParameter(Constants.X);
        String v2 = request.getParameter(Constants.V2);
        String X1 = request.getParameter(Constants.X1);
        String v12 = request.getParameter(Constants.V12);
        String X2 = request.getParameter(Constants.X2);

        if (Utils.areStringsValid(idParam, dateParam, team1IDParam, team2IDParam, v1, X, v2, X1, v12, X2)) {
            try {
                int id = Integer.parseInt(idParam);
                Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateParam);
                java.sql.Date date = new java.sql.Date(utilDate.getTime());
                int team1_id = Integer.parseInt(team1IDParam);
                int team2_id = Integer.parseInt(team2IDParam);
                double victory1 = Double.parseDouble(v1);
                double draw = Double.parseDouble(X);
                double victory2 = Double.parseDouble(v2);
                double victory1orDraw = Double.parseDouble(X1);
                double victory1or2 = Double.parseDouble(v12);
                double victory2orDraw = Double.parseDouble(X2);
                if (!checkCoefficientsValues(victory1, draw, victory2, victory1orDraw, victory1or2, victory2orDraw)) {
                    logger.warn("Coefficient values are not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.COEF_VALUE_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                Match match = new Match();
                match.setId(id);
                match.setDate(date);
                match.setTeam1_id(team1_id);
                match.setTeam2_id(team2_id);
                match.setVictory1(victory1);
                match.setDraw(draw);
                match.setVictory2(victory2);
                match.setVictory1OrDraw(victory1orDraw);
                match.setVictory1Or2(victory1or2);
                match.setVictory2OrDraw(victory2orDraw);
                matchDao.update(match);

                logger.info("match has been updated");
                request.getSession().setAttribute(Constants.MATCHES_MESSAGE, Constants.UPDATE_MATCH_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_MATCHES);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (ParseException e) {
                logger.error("Cannot parse a date parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DATE_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during update match operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_MATCH_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Update match parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Deletes a match entry through {@link by.academy.it.dao.MatchDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void deleteMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(key)) {
            try {
                int id = Integer.parseInt(key);
                matchDao.delete(id);

                logger.info("match has been deleted");
                request.getSession().setAttribute(Constants.MATCHES_MESSAGE, Constants.DELETE_MATCH_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_MATCHES);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during delete match operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_MATCH_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Delete match parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Checks if values of coefficients is within the acceptable range.
     *
     * @param values coefficients values.
     * @return true if the values greater or equal one and less or equals four, false otherwise.
     */
    private boolean checkCoefficientsValues(double... values) {
        for (double coef : values) {
            if (coef < 1 || coef > 4) {
                return false;
            }
        }
        return true;
    }

}
