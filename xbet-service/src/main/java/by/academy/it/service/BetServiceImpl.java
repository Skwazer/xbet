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
        String type = request.getParameter(Constants.TYPE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            if (!Utils.isStringValid(type)) {
                type = Constants.ACTIVE;
            }
            if (isTypeValid(type)) {
                User user = (User) request.getSession().getAttribute(Constants.USER);
                try {
                    List<Bet> list = null;
                    double pages = 0d;
                    switch (type) {
                        case Constants.ACTIVE:
                            list = betDao.findActiveBetsByUserId(user.getId(), startFrom);
                            pages = Math.ceil(betDao.getAmountOfActiveUserBets(user.getId()) / 10d);
                            break;
                        case Constants.PLAYED:
                            list = betDao.findPlayedBetsByUserId(user.getId(), startFrom);
                            pages = Math.ceil(betDao.getAmountOfPlayedUserBets(user.getId()) / 10d);
                            break;
                    }
                    if (!list.isEmpty()) {
                        ServiceFactoryImpl.getModelService().setBetMatches(list);

                        request.setAttribute(Constants.CURRENT_PAGE, page);
                        request.setAttribute(Constants.PAGES, pages);
                        logger.info("bets list is retrieved");
                    }
                    request.setAttribute(Constants.TYPE, type);
                    request.setAttribute(Constants.BETS, list);
                    request.getRequestDispatcher(Constants.PATH + Constants.BETS + Constants.JSP).
                            forward(request, response);

                } catch (DAOException e) {
                    logger.error("An exception occurred during get bets list operation", e);
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_EXCEPTION);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } else {
                logger.error("Bets type parameter is not valid");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Checks if the type parameter has an appropriate value.
     *
     * @param type type of bets.
     * @return true if type is 'active' or 'played', false otherwise.
     */
    private boolean isTypeValid(String type) {
        return Constants.ACTIVE.equals(type) || Constants.PLAYED.equals(type);
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

    /**
     * Deletes played user's bets.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void deletePlayedBets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(idParam)) {
            try {
                Integer userId = Integer.parseInt(idParam);

                betDao.deletePlayedBets(userId);
                logger.info("played bets have been deleted");

                response.sendRedirect(Utils.getReferrerURI(request) + Constants.TYPE_PLAYED);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);

            } catch (DAOException e) {
                logger.error("An exception occurred during delete played bets operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BETS_DELETE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("User id parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }

    }

}
