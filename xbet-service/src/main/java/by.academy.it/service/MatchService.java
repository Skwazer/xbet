package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.dao.TeamDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.MatchDao} and {@link by.academy.it.dao.TeamDao}.
 *
 */
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private MatchDao matchDao = DaoFactory.getInstance().getMatchDao();
    private TeamDao teamDao = DaoFactory.getInstance().getTeamDao();
    private static MatchService instance;

    /**
     * Prohibits creating instance of class outside the class.
     */
    private MatchService() {
    }


    /**
     * Creates {@code MatchService} instance if it is not created and returns it.
     *
     * @return {@code MatchService} instance.
     */
    public static MatchService getInstance() {
        if (instance == null) {
            instance = new MatchService();
            logger.info("MatchService instance has been created");
        }
        return instance;
    }


    /**
     * Retrieves a list of unpalyed match entities through {@link by.academy.it.dao.MatchDao}.
     *
     * @return {@code List<Match>} - the list of unplayed matches.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public List<Match> getUnplayedMatches() throws ServiceException {
        List<Match> list;
        try {
            list = matchDao.getUnplayedMatches();
            if (!list.isEmpty()) {
                for (Match match : list) {
                    setTeams(match);
                }
            } else {
                logger.error("MatchService matches list is null");
                throw new ServiceException("MatchService matches list is null");
            }
        } catch (DAOException e) {
            logger.error("MatchService cannot get a matches list", e);
            throw new ServiceException("MatchService cannot get a matches list");
        }
        return list;
    }


    /**
     * Retrieves a match entry by id through {@link by.academy.it.dao.MatchDao}.
     *
     * @param id the id of a match.
     * @return the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public Match getMatchById(int id) throws ServiceException {
        Match match;
        try {
            match = matchDao.findById(id);
            if (match != null) {
                setTeams(match);
            } else {
                logger.error("MatchService match is null");
                throw new ServiceException("MatchService match is null");
            }
        } catch (DAOException e) {
            logger.error("MatchService cannot get a match by id", e);
            throw new ServiceException("MatchService cannot get a matchby id");
        }
        return match;
    }


    /**
     * Finds teams by {@code team1_id} and {@code team2_id} fields
     * and sets them to {@code team1} and {@code team2} fields.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void setTeams(Match match) throws DAOException {
        match.setTeam1(teamDao.findById(match.getTeam1_id()));
        match.setTeam2(teamDao.findById(match.getTeam2_id()));
    }

}
