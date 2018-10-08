package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.ResultDao;
import by.academy.it.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.ResultDao} class.
 *
 */
class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);
    private ResultDao resultDao;

    /**
     * Constructs an instance of the {@code ResultService}.
     *
     * @param resultDao a ResultDao instance.
     */
    ResultServiceImpl(ResultDao resultDao) {
        this.resultDao = resultDao;
        logger.info("ResultService has been created");
    }


    /**
     * Retrieves a list of all results, puts it in the session and sends to 'results' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                List<Result> list = resultDao.getResults(startFrom);
                double pages = Math.ceil(resultDao.getAmountOfAllResults() / 10d);
                if (!list.isEmpty()) {
                    logger.info("Results have been found");
                    request.setAttribute(Constants.RESULTS_LIST, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    request.getRequestDispatcher(Constants.PATH + Constants.GET_RESULTS + Constants.JSP)
                            .forward(request, response);
                } else {
                    logger.warn("Results have not been found");
                    request.setAttribute(Constants.RESULTS_MESSAGE, Constants.RESULTS_LIST_EMPTY);

                    request.getRequestDispatcher(Constants.PATH + Constants.GET_RESULTS + Constants.JSP)
                            .forward(request, response);
                }
            } catch (DAOException e) {
                logger.error("An exception occurred during get all results operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.RESULTS_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Retrieves a list of last results, puts it in the session and sends to 'results' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showLastResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                double pages = Math.ceil(resultDao.getAmountOfAllResults() / 10d);
                List<Result> list = resultDao.getLastResults(startFrom);
                if (!list.isEmpty()) {
                    logger.info("Results have been found");
                    ServiceFactoryImpl.getModelService().setResultMatches(list);

                    request.setAttribute(Constants.RESULTS_LIST, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    request.getRequestDispatcher(Constants.PATH + Constants.RESULTS + Constants.JSP)
                            .forward(request, response);
                } else {
                    logger.warn("Results have not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.RESULTS_LIST_EMPTY);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } catch (DAOException e) {
                logger.error("An exception occurred during get all results operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.RESULTS_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Creates a result through {@link by.academy.it.dao.ResultDao}
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String matchIdParam = request.getParameter(Constants.MATCH_ID_PARAM);
        String team1GoalsParam = request.getParameter(Constants.TEAM1_GOALS);
        String team2GoalsParam = request.getParameter(Constants.TEAM2_GOALS);
        String result = request.getParameter(Constants.RESULT);

        if (Utils.areStringsValid(matchIdParam, team1GoalsParam, team2GoalsParam, result)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);
                int team1_goals = Integer.parseInt(team1GoalsParam);
                int team2_goals = Integer.parseInt(team2GoalsParam);
                if (!checkGoalsValue(team1_goals) || !checkGoalsValue(team2_goals)) {
                    logger.warn("Goal values are not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.GOALS_VALUE_ERROR);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }

                Result res = new Result();
                res.setMatchId(matchId);
                res.setTeam1_goals(team1_goals);
                res.setTeam2_goals(team2_goals);
                res.setResult(result);
                resultDao.create(res);

                logger.info("result has been created");
                request.getSession().setAttribute(Constants.RESULTS_MESSAGE, Constants.CREATE_RESULT_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_RESULTS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during create result operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_RESULT_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Create result parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a result by id through {@link by.academy.it.dao.ResultDao} and sends forward to 'update result' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showUpdateResultPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(key)) {
            try {
                int id = Integer.parseInt(key);
                Result result = resultDao.findById(id);
                logger.info("result has been retrieved");
                request.setAttribute(Constants.UPDATED_RESULT, result);

                List<Integer> matchesIds = ServiceFactoryImpl.getIdService().getMatchesIds();
                if (matchesIds != null) {
                    request.setAttribute(Constants.MATCHES_IDS, matchesIds);
                    logger.info("matches ids have been retrieved");
                } else {
                    logger.warn("Match ids have not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCHES_IDS_NOT_FOUND);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                request.getRequestDispatcher(Constants.PATH + Constants.UPDATE_RESULT + Constants.JSP)
                        .forward(request, response);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during show update result page operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_RESULT_ERROR);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Show update result page operation parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a list of results' ids and sends forward to 'create result' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showCreateResultPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            List<Integer> matchesIds = ServiceFactoryImpl.getIdService().getMatchesIds();
            if (matchesIds != null) {
                request.setAttribute(Constants.MATCHES_IDS, matchesIds);
                logger.info("matches ids have been retrieved");
            } else {
                logger.warn("Matches id have not been found");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCHES_IDS_NOT_FOUND);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
                return;
            }
            request.getRequestDispatcher(Constants.PATH + Constants.CREATE_RESULT + Constants.JSP)
                    .forward(request, response);

        } catch (DAOException e) {
            logger.error("An exception occurred during show update result page operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_RESULT_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates a result entry through {@link by.academy.it.dao.ResultDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void updateResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter(Constants.ID);
        String matchIdParam = request.getParameter(Constants.MATCH_ID_PARAM);
        String team1GoalsParam = request.getParameter(Constants.TEAM1_GOALS);
        String team2GoalsParam = request.getParameter(Constants.TEAM2_GOALS);
        String result = request.getParameter(Constants.RESULT);

        if (Utils.areStringsValid(idParam, matchIdParam, team1GoalsParam, team2GoalsParam, result)) {
            try {
                int id = Integer.parseInt(idParam);
                int matchId = Integer.parseInt(matchIdParam);
                int team1_goals = Integer.parseInt(team1GoalsParam);
                int team2_goals = Integer.parseInt(team2GoalsParam);
                if (!checkGoalsValue(team1_goals) || !checkGoalsValue(team2_goals)) {
                    logger.warn("Goals values are not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.GOALS_VALUE_ERROR);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }

                Result res = new Result();
                res.setId(id);
                res.setMatchId(matchId);
                res.setTeam1_goals(team1_goals);
                res.setTeam2_goals(team2_goals);
                res.setResult(result);
                resultDao.update(res);

                logger.info("result has been updated");
                request.getSession().setAttribute(Constants.RESULTS_MESSAGE, Constants.UPDATE_RESULT_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_RESULTS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during update result operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_RESULT_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Update result parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Deletes a result entry through {@link by.academy.it.dao.ResultDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void deleteResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(key)) {
            try {
                int id = Integer.parseInt(key);
                resultDao.delete(id);

                logger.info("result has been deleted");
                request.getSession().setAttribute(Constants.RESULTS_MESSAGE, Constants.DELETE_RESULT_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_RESULTS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during delete result operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_RESULT_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Delete result parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Checks if the value of the goals is within the acceptable range.
     *
     * @param value the goals value.
     * @return true if the value greater than zero and less or equals five, false otherwise.
     */
    private boolean checkGoalsValue(double value) {
        return value >= 0 && value <= 6;
    }

}
