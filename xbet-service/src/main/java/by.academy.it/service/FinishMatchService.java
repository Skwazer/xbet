package by.academy.it.service;

import by.academy.it.entity.Bet;
import by.academy.it.entity.Match;
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
public class FinishMatchService implements Service {

    private static final Logger logger = LoggerFactory.getLogger(FinishMatchService.class);
    private static FinishMatchService instance;

    private static BetService betService = BetService.getInstance();
    private static UserService userService = UserService.getInstance();


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
        String matchIdParam = request.getParameter(MATCH_ID);
        if (isValidString(matchIdParam)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);
                Match match = MatchService.getInstance().getMatchById(matchId);

                Result result = new Result();
                result.setMatchId(matchId);
                ResultEntry resultEntry = new ResultEntry();
                result.setResult(resultEntry.resultSymbol);
                if (resultEntry.resultSymbol == FIRST_WON){
                    result.setWinnerId(match.getTeam1_id());
                    result.setLoserId(match.getTeam2_id());
                    result.setWinnerGoals(resultEntry.team1goals);
                    result.setLoserGoals(resultEntry.team2goals);
                } else {
                    result.setWinnerId(match.getTeam2_id());
                    result.setLoserId(match.getTeam1_id());
                    result.setWinnerGoals(resultEntry.team2goals);
                    result.setLoserGoals(resultEntry.team1goals);
                }
                ResultService.getInstance().createResult(result);
                logger.info("result has been created - " + result);

                checkBets(matchId, result.getResult());

                HttpSession session = request.getSession();
                User user = userService.findUserByLogin(((User) session.getAttribute(USER)).getLogin());
                User sessionUser = (User) session.getAttribute(USER);
                if (!user.getBalance().equals(sessionUser.getBalance())) {
                    sessionUser.setBalance(user.getBalance());
                    session.setAttribute(USER, sessionUser);
                    logger.info("user balance in the session has been updated");
                }

                response.sendRedirect(getReferrerURI(request));
            } catch (Exception e) {
                logger.error("An exception occurred during finish match operation", e);
                request.getSession().setAttribute(ERROR_MESSAGE, FINISH_ERROR);

                response.sendRedirect(request.getContextPath() + ERROR);
            }
        } else {
            logger.error("Match id parameter is not valid");
            request.getSession().setAttribute(ERROR_MESSAGE, MATCH_ID_ERROR);

            response.sendRedirect(request.getContextPath() + ERROR);
        }
    }


    /**
     * Finds all active bets, placed on this match, sets a bet status to
     * 'won' or 'lost' and updates the user balance.
     *
     * @param matchId the id of a match.
     * @param result string representation of a match result: '1' or 'X' or '2'.
     * @throws ServiceException if an exception occurred during the operation.
     */
    private void checkBets(int matchId, char result) throws ServiceException {
        List<Bet> list = betService.getMatchBets(matchId);
        if (!list.isEmpty()) {
            for (Bet bet : list) {
                for (char c : bet.getBetResult().toCharArray()) {
                    if (result == c) {
                        bet.setStatus(WON);
                        User user = userService.findUserById(bet.getUser_id());
                        userService.updateUserBalance(user.getLogin(), bet.getMoney() * bet.getBet());
                        logger.info("user balance in the database has been updated");
                        break;
                    } else {
                        bet.setStatus(LOST);
                    }
                }
                betService.updateBet(bet);
                logger.info("bet with id [" + bet.getId() + "] - " + bet.getStatus());
            }
        } else {
            logger.warn("no bets placed on this match");
        }
    }


    /**
     * This class represents a random result of a match.
     */
    private class ResultEntry {
        int team1goals;
        int team2goals;
        char resultSymbol;
        ResultEntry() {
            getRandomResult();
        }

        void getRandomResult() {
            Random random = new Random();
            team1goals = random.nextInt(6);
            team2goals = random.nextInt(6);

            if (team1goals > team2goals) {
                resultSymbol = FIRST_WON;
            } else if (team1goals < team2goals) {
                resultSymbol = SECOND_WON;
            } else {
                resultSymbol = DRAW;
            }
        }
    }
}
