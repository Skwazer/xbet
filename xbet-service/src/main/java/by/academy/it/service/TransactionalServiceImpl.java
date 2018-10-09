package by.academy.it.service;

import by.academy.it.dao.BetDao;
import by.academy.it.dao.DAOException;
import by.academy.it.dao.TransactionalDao;
import by.academy.it.dao.UserDao;
import by.academy.it.entity.Bet;
import by.academy.it.entity.Result;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * This class performs transactional operations through {@link by.academy.it.dao.TransactionalDao}.
 *
 */
class TransactionalServiceImpl implements TransactionalService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalServiceImpl.class);

    private BetDao betDao;
    private TransactionalDao transactionalDao;
    private UserDao userDao;


    /**
     * Constructs an instance of the {@code TransactionalService}.
     *
     * @param betDao a BetDao instance.
     * @param transactionalDao a TransactionalDao instance.
     * @param userDao a UserDao instance.
     */
    TransactionalServiceImpl(BetDao betDao, TransactionalDao transactionalDao, UserDao userDao) {
        this.betDao = betDao;
        this.transactionalDao = transactionalDao;
        this.userDao = userDao;
        logger.info("TransactionalService has been created");
    }


    /**
     * Withdraws user's money and creates a bet entry.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void confirmBet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String matchIdParam = request.getParameter(Constants.MATCH_ID);
        String betParam = request.getParameter(Constants.BET);
        String amountParam = request.getParameter(Constants.AMOUNT);
        if (Utils.areStringsValid(matchIdParam, betParam, amountParam)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);
                double amount = Double.parseDouble(amountParam);
                if (amount <=0) {
                    logger.warn("Amount is zero or negative");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.AMOUNT_NEGATIVE);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                String[] betParams = betParam.split(Constants.SLASH);
                double betValue = Double.parseDouble(betParams[1]);

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(Constants.USER);

                double balance;
                if (user.getBalance() >= amount) {
                    balance = user.getBalance() - amount;
                } else {
                    logger.warn("The user does not have enough money");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BALANCE_NOT_ENOUGH);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }

                Bet bet = new Bet();
                bet.setUser_id(user.getId());
                bet.setMatch_id(matchId);
                bet.setBetResult(betParams[0]);
                bet.setBet(betValue);
                bet.setMoney(amount);
                bet.setStatus(Constants.ACTIVE);

                logger.debug("start of transaction");
                transactionalDao.placeBet(user.getLogin(), balance, bet);
                logger.debug("transaction is finished");
                user.setBalance(balance);

                logger.info("bet has been placed");
                request.setAttribute(Constants.CONFIRM_MESSAGE, Constants.SUCCESS);
                request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during confirm bet operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BET_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Bet parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BET_PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }

    /**
     * Updates the session user's balance if it is necessary.
     *
     * @param request HttpServletRequest request.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    private void checkSessionUserBalance(HttpServletRequest request) throws DAOException {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(Constants.USER);
        User user = userDao.findByLogin(sessionUser.getLogin());
        if (!user.getBalance().equals(sessionUser.getBalance())) {
            sessionUser.setBalance(user.getBalance());
            logger.info("user balance in the session has been updated");
        }
    }


    /**
     * Finds all active bets, placed on this match, sets a bet status to 'won' or 'lost'. Creates result entry,
     * updates bet entries and user balance through {@link by.academy.it.dao.TransactionalDao}.
     *
     * @param result {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    private void finishMatch(Result result) throws DAOException {
            List<Bet> bets = betDao.findByMatchId(result.getMatchId());
            if (!bets.isEmpty()) {
                bets.forEach(bet -> {
                    if (bet.getBetResult().contains(result.getResult())) {
                        bet.setStatus(Constants.WON);
                    } else {
                        bet.setStatus(Constants.LOST);
                    }
                });
            } else {
                logger.warn("no bets placed on this match");
            }

            logger.debug("start of a transaction");
            transactionalDao.finishMatch(result, bets);
            logger.debug("transaction is finished");
    }


    /**
     * Creates a result entity and finishes the match.
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

                if (!ResultServiceImpl.checkGoalsValue(team1_goals) ||
                        !ResultServiceImpl.checkGoalsValue(team2_goals)) {
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

                finishMatch(res);
                checkSessionUserBalance(request);

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
     * Creates a random result entity to the selected match and finishes the match.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createRandomResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String matchIdParam = request.getParameter(Constants.MATCH_ID);
        if (Utils.isStringValid(matchIdParam)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);

                Result result = new Result();
                result.setMatchId(matchId);
                ResultEntry resultEntry = new ResultEntry();
                result.setResult(resultEntry.resultSymbol);
                result.setTeam1_goals(resultEntry.team1goals);
                result.setTeam2_goals(resultEntry.team2goals);

                finishMatch(result);
                checkSessionUserBalance(request);

                logger.info("random result has been created");
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.RESULTS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);

            } catch (DAOException e) {
                logger.error("An exception occurred during create random result operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.RANDOM_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Match id parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.MATCH_ID_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * This class represents a random result of a match.
     */
    private class ResultEntry {
        int team1goals;
        int team2goals;
        String resultSymbol;
        ResultEntry() {
            getRandomResult();
        }

        void getRandomResult() {
            Random random = new Random();
            team1goals = random.nextInt(6);
            team2goals = random.nextInt(6);

            if (team1goals > team2goals) {
                resultSymbol = Constants.FIRST_WON;
            } else if (team1goals < team2goals) {
                resultSymbol = Constants.SECOND_WON;
            } else {
                resultSymbol = Constants.DRAW;
            }
        }
    }

}
