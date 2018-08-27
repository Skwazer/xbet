package by.academy.it.service;

import by.academy.it.dao.BetDao;
import by.academy.it.dao.TransactionalDao;
import by.academy.it.dao.UserDao;
import by.academy.it.dao.factory.ConnectionPoolImpl;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Bet;
import by.academy.it.entity.Result;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * This class randomly finishes the match.
 *
 */
public class FinishMatchService {

    private static final Logger logger = LoggerFactory.getLogger(FinishMatchService.class);
    private static FinishMatchService instance;

    private BetDao betDao = DaoFactory.getInstance(ConnectionPoolImpl.getInstance()).getBetDao();
    private TransactionalDao transactionalDao = DaoFactory.getInstance(ConnectionPoolImpl.getInstance()).getTransactionalDao();
    private UserDao userDao = DaoFactory.getInstance(ConnectionPoolImpl.getInstance()).getUserDao();


    /**
     * Prohibits creating an instance of class outside the class.
     */
    private FinishMatchService() {
    }


    /**
     * Creates {@code FinishMatchService} instance if it is not created and returns it.
     *
     * @return {@code FinishMatchService} instance.
     */
    public static FinishMatchService getInstance() {
        if (instance == null) {
            instance = new FinishMatchService();
            logger.info("FinishMatchService instance has been created");
        }
        return instance;
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

            } catch (Exception e) {
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
