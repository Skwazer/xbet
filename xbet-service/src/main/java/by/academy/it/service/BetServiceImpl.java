package by.academy.it.service;

import by.academy.it.dao.BetDao;
import by.academy.it.dao.DAOException;
import by.academy.it.entity.Bet;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.BetDao}.
 *
 */
class BetServiceImpl implements BetService {

    private static final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);
    private BetDao betDao;

    /**
     * Constructs an instance of the {@code BetService}.
     */
    BetServiceImpl(BetDao betDao) {
        this.betDao = betDao;
        logger.info("BetService has been created");
    }


    /**
     * Creates a bet entry through {@link by.academy.it.dao.BetDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createBet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdParam = request.getParameter(Constants.USER_ID);
        String matchIdParam = request.getParameter(Constants.MATCH_ID);
        String betResult = request.getParameter(Constants.BET_RESULT);
        String betParam = request.getParameter(Constants.BET);
        String moneyParam = request.getParameter(Constants.MONEY);
        String status = request.getParameter(Constants.STATUS);

        if (Utils.isValidString(userIdParam) && Utils.isValidString(matchIdParam) && Utils.isValidString(betResult)
                && Utils.isValidString(betParam) && Utils.isValidString(moneyParam) && Utils.isValidString(status)) {
            try {
                int userId = Integer.parseInt(userIdParam);
                int matchId = Integer.parseInt(matchIdParam);
                double bet = Double.parseDouble(betParam);
                double money = Double.parseDouble(moneyParam);
                if (!checkBetValue(bet)) {
                    logger.warn("Bet value is not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BET_VALUE_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                if (money < 0) {
                    logger.warn("Money value is not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MONEY_VALUE_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                Bet bet1 = new Bet();
                bet1.setUser_id(userId);
                bet1.setMatch_id(matchId);
                bet1.setBetResult(betResult);
                bet1.setBet(bet);
                bet1.setMoney(money);
                bet1.setStatus(status);
                betDao.create(bet1);

                logger.info("bet has been created");
                request.getSession().setAttribute(Constants.BETS_MESSAGE, Constants.CREATE_BET_MESSAGE);
                response.sendRedirect( request.getContextPath() + Constants.MAIN + Constants.GET + Constants.BETS);

            } catch (Exception e) {
                logger.error("An exception occurred during create bet operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_BET_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Create bet data are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_DATA_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a bet by id through {@link by.academy.it.dao.BetDao} and sends a redirect to 'update bet' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void showUpdateBetPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isValidString(key)) {
            try {
                int id = Integer.parseInt(key);
                Bet bet = betDao.getById(id);
                logger.info("bet has been retrieved");

                request.getSession().setAttribute(Constants.UPDATED_BET, bet);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.UPDATE + Constants.BET);

            } catch (Exception e) {
                logger.error("An exception occurred during show update bet page operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_BET_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Show update bet page operation parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_BET_PARAMETER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates a bet entry through {@link by.academy.it.dao.BetDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void updateBet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter(Constants.ID);
        String userIdParam = request.getParameter(Constants.USER_ID);
        String matchIdParam = request.getParameter(Constants.MATCH_ID);
        String betResult = request.getParameter(Constants.BET_RESULT);
        String betParam = request.getParameter(Constants.BET);
        String moneyParam = request.getParameter(Constants.MONEY);
        String status = request.getParameter(Constants.STATUS);
        if (Utils.isValidString(userIdParam) && Utils.isValidString(matchIdParam) && Utils.isValidString(betResult)
                && Utils.isValidString(betParam) && Utils.isValidString(moneyParam) && Utils.isValidString(status)) {
            try {
                int id = Integer.parseInt(idParam);
                int userId = Integer.parseInt(userIdParam);
                int matchId = Integer.parseInt(matchIdParam);
                double bet = Double.parseDouble(betParam);
                double money = Double.parseDouble(moneyParam);
                if (!checkBetValue(bet)) {
                    logger.warn("Bet value is not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BET_VALUE_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                if (money < 0) {
                    logger.warn("Money value is not correct");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MONEY_VALUE_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                Bet bet1 = new Bet();
                bet1.setId(id);
                bet1.setUser_id(userId);
                bet1.setMatch_id(matchId);
                bet1.setBetResult(betResult);
                bet1.setBet(bet);
                bet1.setMoney(money);
                bet1.setStatus(status);
                betDao.update(bet1);

                logger.info("bet has been updated");
                request.getSession().setAttribute(Constants.BETS_MESSAGE, Constants.UPDATE_BET_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.BETS);

            } catch (Exception e) {
                logger.error("An exception occurred during update bet operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_BET_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Update bet parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a list of the user's bets and sends it to 'bets page'.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void showUserBets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            User user = (User) request.getSession().getAttribute(Constants.USER);
            try {
                List<Bet> list = betDao.findByUserId(user.getId(), startFrom);
                if (!list.isEmpty()) {
                    ServiceFactoryImpl.getModelService().setBetMatches(list);
                    double pages = Math.ceil(betDao.getAmountOfUserBets(user.getId()) / 10d);

                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);
                    logger.info("bets list is retrieved");
                }
                request.setAttribute(Constants.BETS, list);
                request.getRequestDispatcher(Constants.PATH + Constants.BETS + Constants.JSP).
                        forward(request, response);

            } catch (Exception e) {
                logger.error("An exception occurred during get bets list operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_EXCEPTION);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Retrieves a list of all bets and sends it to 'bets page'.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void showAllBets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            try {
                List<Bet> list = betDao.findAll(startFrom);
                if (!list.isEmpty()) {
                    double pages = Math.ceil(betDao.getAmountOfAllBets() / 10d);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    logger.info("all bets list is retrieved");
                }
                request.setAttribute(Constants.ALL_BETS, list);
                request.getRequestDispatcher(Constants.PATH + Constants.GET + Constants.BETS + Constants.JSP)
                        .forward(request, response);

            } catch (Exception e) {
                logger.error("An exception occurred during get all bets list operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_EXCEPTION);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Deletes a bet entry through {@link by.academy.it.dao.BetDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void deleteBet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isValidString(key)) {
            try {
                int id = Integer.parseInt(key);
                betDao.delete(id);

                logger.info("bet has been deleted");
                request.getSession().setAttribute(Constants.BETS_MESSAGE, Constants.DELETE_BET_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.BETS);

            } catch (DAOException e) {
                logger.error("An exception occurred during delete bet operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_ROLE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Delete bet parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Checks if the value of the bet is within the acceptable range.
     *
     * @param value the bet value.
     * @return true if the value greater than zero and less or equals five, false otherwise.
     */
    private boolean checkBetValue(double value) {
        return value > 0 && value <= 5;
    }

}
