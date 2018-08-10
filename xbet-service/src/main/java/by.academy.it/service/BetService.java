package by.academy.it.service;

import by.academy.it.dao.BetDao;
import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Bet;
import by.academy.it.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.BetDao} and {@link by.academy.it.dao.MatchDao}.
 *
 */
public class BetService {

    private static final Logger logger = LoggerFactory.getLogger(BetService.class);
    private BetDao betDao = DaoFactory.getInstance().getBetDao();
    private MatchDao matchDao = DaoFactory.getInstance().getMatchDao();
    private static BetService instance;

    /**
     * Prohibits creating instance of class outside the class.
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
            throw new ServiceException("BetService cannot create a bet");
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
            betDao.update(bet);
        } catch (DAOException e) {
            logger.error("BetService cannot update a bet", e);
            throw new ServiceException("BetService cannot update a bet");
        }
    }


    /**
     * Retrieves a list of user's bets through {@link by.academy.it.dao.BetDao}.
     *
     * @param userId user's id.
     * @return {@code List<Bet>} - a list of {@link by.academy.it.entity.Bet} entities.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public List<Bet> getUsersBets(int userId) throws ServiceException {
        List<Bet> list;
        try {
            list = betDao.findByUserId(userId);
            if (!list.isEmpty()) {
                for (Bet bet : list) {
                    Match match = matchDao.findById(bet.getMatch_id());
                    MatchService.getInstance().setTeams(match);
                    bet.setMatch(match);
                }
            }
        } catch (DAOException e) {
            logger.error("BetService cannot get a bets list", e);
            throw new ServiceException("BetService cannot get a bets list");
        }
        return list;
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
            throw new ServiceException("BetService cannot get a bets list");
        }
        return list;
    }

}
