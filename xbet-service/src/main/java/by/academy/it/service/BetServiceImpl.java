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
     *
     * @param betDao a BetDao instance.
     */
    BetServiceImpl(BetDao betDao) {
        this.betDao = betDao;
        logger.info("BetService has been created");
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

            } catch (DAOException e) {
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

            } catch (DAOException e) {
                logger.error("An exception occurred during get all bets list operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_EXCEPTION);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }

}
