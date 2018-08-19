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
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public void createBet(Bet bet) throws ServiceException {
        try {
            betDao.create(bet);
        } catch (DAOException e) {
            logger.error("BetService cannot create a bet", e);
            throw new ServiceException("BetService cannot create a bet", e);
        }
    }


    /**
     * Updates a bet entry through {@link by.academy.it.dao.BetDao}.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public void updateBet(Bet bet) throws ServiceException {
        try {
            betDao.updateStatus(bet);
        } catch (DAOException e) {
            logger.error("BetService cannot updateBalance a bet", e);
            throw new ServiceException("BetService cannot updateBalance a bet", e);
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

}
