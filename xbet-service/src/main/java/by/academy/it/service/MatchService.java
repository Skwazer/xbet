package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.dao.TeamDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.MatchDao} and {@link by.academy.it.dao.TeamDao}.
 *
 */
public class MatchService{

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private static MatchService instance;
    private MatchDao matchDao = DaoFactory.getInstance().getMatchDao();
    private TeamDao teamDao = DaoFactory.getInstance().getTeamDao();

    /**
     * Prohibits creating an instance of class outside the class.
     */
    private MatchService() {
    }


    /**
     * Creates {@code MatchService} instance if it is not created and returns it.
     *
     * @return {@code MatchService} instance.
     */
    public static MatchService getInstance() {
        if (instance == null) {
            instance = new MatchService();
            logger.info("MatchService instance has been created");
        }
        return instance;
    }


    /**
     * Retrieves a match entry by id through {@link by.academy.it.dao.MatchDao}.
     *
     * @param id the id of a match.
     * @return the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public Match getMatchById(int id) throws ServiceException {
        Match match;
        try {
            match = matchDao.findById(id);
            if (match != null) {
                setTeams(match);
            } else {
                logger.error("MatchService match is null");
                throw new ServiceException("MatchService match is null");
            }
        } catch (DAOException e) {
            logger.error("MatchService cannot get a match by id", e);
            throw new ServiceException("MatchService cannot get a matchby id", e);
        }
        return match;
    }


    /**
     * Finds teams by {@code team1_id} and {@code team2_id} fields
     * and sets them to {@code team1} and {@code team2} fields.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void setTeams(Match match) throws DAOException {
        match.setTeam1(teamDao.findById(match.getTeam1_id()));
        match.setTeam2(teamDao.findById(match.getTeam2_id()));
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
        if (Utils.isValidString(matchId)) {
            try {
                int id = Integer.parseInt(matchId);
                Match match = getMatchById(id);
                logger.info("match has been retrieved - [" + match.getId() + "]");
                request.getSession().setAttribute(Constants.MATCH, match);

                response.sendRedirect(request.getContextPath() + Constants.MAIN_BET);

            } catch (Exception e) {
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
    public void showUnplayedMatches(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                List<Match> list = getUnplayedMatches(startFrom);
                double pages = Math.ceil(matchDao.getAmountOfUnplayedMatches() / 10d);
                if (!list.isEmpty()) {
                    logger.info("Matches have been found");
                    request.setAttribute(Constants.MATCHES_LIST, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    request.getRequestDispatcher(Constants.PATH + Constants.MATCHES + Constants.JSP).forward(request, response);
                } else {
                    logger.warn("Matches have not been found");
                    request.setAttribute(Constants.MATCHES_MESSAGE, Constants.MATCHES_LIST_EMPTY);

                    request.getRequestDispatcher(Constants.PATH + Constants.MATCHES + Constants.JSP).forward(request, response);
                }
            } catch (ServiceException | DAOException e) {
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
    public void showMatches(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
     * Retrieves a list of unpalyed match entities through {@link by.academy.it.dao.MatchDao}.
     *
     * @param startFrom a position from which the select operation is performed.
     * @return {@code List<Match>} - the list of unplayed matches.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    List<Match> getUnplayedMatches(int startFrom) throws ServiceException {
        List<Match> list;
        try {
            list = matchDao.getUnplayedMatches(startFrom);
            if (!list.isEmpty()) {
                for (Match match : list) {
                    setTeams(match);
                }
            }
        } catch (DAOException e) {
            logger.error("MatchService cannot get a matches list", e);
            throw new ServiceException("MatchService cannot get a matches list", e);
        }
        return list;
    }


    /**
     * Creates a match through {@link by.academy.it.dao.MatchDao}
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
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

        } catch (Exception e) {
            logger.error("An exception occurred during create match operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_MATCH_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a match by id through {@link by.academy.it.dao.MatchDao} and sends a redirect to 'update match' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void showUpdateMatchPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        try {
            int id = Integer.parseInt(key);
            Match match = matchDao.findById(id);
            logger.info("match has been retrieved");

            request.getSession().setAttribute(Constants.UPDATED_MATCH, match);
            response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.UPDATE_MATCH);

        } catch (Exception e) {
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

        } catch (Exception e) {
            logger.error("An exception occurred during update match operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_MATCH_ERROR);

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
        try {
            int id = Integer.parseInt(key);
            matchDao.delete(id);

            logger.info("match has been deleted");
            request.getSession().setAttribute(Constants.MATCHES_MESSAGE, Constants.DELETE_MATCH_MESSAGE);
            response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_MATCHES);

        } catch (DAOException e) {
            logger.error("An exception occurred during delete match operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_MATCH_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


}
