package by.academy.it.service;

import by.academy.it.dao.BetDao;
import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Bet;
import by.academy.it.entity.Match;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.BetDao} and {@link by.academy.it.dao.MatchDao}.
 *
 */
public class BetService {

    private static final Logger logger = LoggerFactory.getLogger(BetService.class);
    private static BetService instance;
    private BetDao betDao = DaoFactory.getInstance().getBetDao();
    private MatchDao matchDao = DaoFactory.getInstance().getMatchDao();

    /**
     * Prohibits creating an instance of class outside the class.
     */
    private BetService() {
    }


    /**
     * Creates {@code BetService} instance if it is not created and returns it.
     *
     * @return {@code BetService} instance.
     */
    public static BetService getInstance() {
        if (instance == null) {
            instance = new BetService();
            logger.info("BetService instance has been created");
        }
        return instance;
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
        try {
            int id = Integer.parseInt(idParam);
            int userId = Integer.parseInt(userIdParam);
            int matchId = Integer.parseInt(matchIdParam);
            double bet = Double.parseDouble(betParam);
            double money = Double.parseDouble(moneyParam);

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
    }


    /**
     * Retrieves a list of bets placed on the match through {@link by.academy.it.dao.BetDao}.
     *
     * @param matchId the id of a match.
     * @return {@code List<Bet>} - a list of {@link by.academy.it.entity.Bet} entities.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public List<Bet> getMatchBets(int matchId) throws ServiceException {
        List<Bet> list;
        try {
            list = betDao.findByMatchId(matchId);
            if (!list.isEmpty()) {
                for (Bet bet : list) {
                    Match match = matchDao.findById(bet.getMatch_id());
                    MatchService.getInstance().setTeams(match);
                    bet.setMatch(match);
                }
            }
        } catch (DAOException e) {
            logger.error("BetService cannot get a bets list", e);
            throw new ServiceException("BetService cannot get a bets list", e);
        }
        return list;
    }


    /**
     * Retrieves a list of user's bets through {@link by.academy.it.dao.BetDao}.
     *
     * @param userId user's id.
     * @param startFrom a position from which the select operation is performed.
     * @return {@code List<Bet>} - a list of {@link by.academy.it.entity.Bet} entities.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    private List<Bet> getUsersBets(int userId, int startFrom) throws ServiceException {
        List<Bet> list;
        try {
            list = betDao.findByUserId(userId, startFrom);
            if (!list.isEmpty()) {
                setMatches(list);
            }
        } catch (DAOException e) {
            logger.error("BetService cannot get a bets list", e);
            throw new ServiceException("BetService cannot get a bets list", e);
        }
        return list;
    }


    /**
     * Retrieves a list of the user's bets and sends it to 'bets page'.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void showUserBets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            User user = (User) request.getSession().getAttribute(Constants.USER);
            if (user != null) {
                try {
                    List<Bet> list = getUsersBets(user.getId(), startFrom);
                    double pages = Math.ceil(betDao.getAmountOfUserBets(user.getId()) / 10d);
                    request.setAttribute(Constants.BETS, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);
                    logger.info("bets list is retrieved");

                } catch (Exception e) {
                    logger.error("An exception occurred during get bets list operation", e);
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_EXCEPTION);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
            }
            request.getRequestDispatcher(Constants.PATH + Constants.BETS + Constants.JSP).forward(request, response);
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
                    List<Bet> list = getAllBets(startFrom);
                    double pages = Math.ceil(betDao.getAmountOfAllBets() / 10d);
                    request.setAttribute(Constants.ALL_BETS, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);

                    logger.info("all bets list is retrieved");
                    request.getRequestDispatcher(Constants.PATH + Constants.GET + Constants.BETS + Constants.JSP)
                            .forward(request, response);

                } catch (Exception e) {
                    logger.error("An exception occurred during get all bets list operation", e);
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_EXCEPTION);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
        }
    }


    /**
     * Retrieves a list of all bets through {@link by.academy.it.dao.BetDao}.
     *
     * @param startFrom a position from which the select operation is performed.
     * @return {@code List<Bet>} - a list of {@link by.academy.it.entity.Bet} entities.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    private List<Bet> getAllBets(int startFrom) throws ServiceException {
        List<Bet> list;
        try {
            list = betDao.findAll(startFrom);
            if (!list.isEmpty()) {
                setMatches(list);
            }
        } catch (DAOException e) {
            logger.error("BetService cannot get all bets list", e);
            throw new ServiceException("BetService cannot get all bets list", e);
        }
        return list;
    }


    /**
     * Finds a match by the {@code match_id} field and sets it to {@code match} field.
     *
     * @param list - the list of bets.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    private void setMatches(List<Bet> list) throws ServiceException {
        for (Bet bet : list) {
            try {
                Match match = matchDao.findById(bet.getMatch_id());
                MatchService.getInstance().setTeams(match);
                bet.setMatch(match);
            } catch (DAOException e) {
                logger.error("BetService cannot get a match or teams by id", e);
                throw new ServiceException("BetService cannot get a match or teams by id", e);
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
        try {
            int id = Integer.parseInt(key);
            betDao.delete(id);

            logger.info("bet has been deleted");
            request.getSession().setAttribute(Constants.BETS_MESSAGE, Constants.DELETE_BET_MESSAGE);
            response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.BETS);

        } catch (DAOException e) {
            logger.error("An exception occurred during delete role operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_ROLE_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }

}
