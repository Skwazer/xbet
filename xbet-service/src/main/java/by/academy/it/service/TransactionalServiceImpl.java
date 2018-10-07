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
        if (Utils.isValidString(matchIdParam) && Utils.isValidString(betParam) && Utils.isValidString(amountParam)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);
                double amount = Double.parseDouble(amountParam);
                String[] betParams = betParam.split("/");
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
                bet.setStatus("active");

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
     * Creates a random result to selected match, finds all active bets, placed on this match, sets a bet status to
     * 'won' or 'lost' and updates the user balance.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void finishMatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String matchIdParam = request.getParameter(Constants.MATCH_ID);
        if (Utils.isValidString(matchIdParam)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);

                Result result = new Result();
                result.setMatchId(matchId);
                ResultEntry resultEntry = new ResultEntry();
                result.setResult(resultEntry.resultSymbol);
                result.setTeam1_goals(resultEntry.team1goals);
                result.setTeam2_goals(resultEntry.team2goals);

                List<Bet> bets = betDao.findByMatchId(matchId);
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

                logger.debug("start of transaction");
                transactionalDao.finishMatch(result, bets);
                logger.debug("transaction is finished");

                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute(Constants.USER);
                User user = userDao.findByLogin(sessionUser.getLogin());
                if (!user.getBalance().equals(sessionUser.getBalance())) {
                    sessionUser.setBalance(user.getBalance());
                    logger.info("user balance in the session has been updated");
                }
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.RESULTS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during finish match operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.FINISH_ERROR);

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