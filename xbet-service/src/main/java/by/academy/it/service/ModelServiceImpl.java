package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.dao.TeamDao;
import by.academy.it.entity.Bet;
import by.academy.it.entity.Match;
import by.academy.it.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class finds related objects for transferred objects to display them in jsp.
 *
 */
class ModelServiceImpl implements ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);
    private TeamDao teamDao;
    private MatchDao matchDao;

    /**
     * Constructs an instance of the {@code ModelService}.
     *
     * @param teamDao a TeamDao instance.
     * @param matchDao a MatchDao instance.
     */
    ModelServiceImpl(TeamDao teamDao, MatchDao matchDao) {
        this.teamDao = teamDao;
        this.matchDao = matchDao;
        logger.info("ModelService has been created");
    }


    /**
     * Finds teams by {@code team1_id} and {@code team2_id} fields
     * and sets them to {@code team1} and {@code team2} match fields.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void setTeams(Match match) throws DAOException {
        match.setTeam1(teamDao.findById(match.getTeam1_id()));
        match.setTeam2(teamDao.findById(match.getTeam2_id()));
    }


    /**
     * Finds a match by the {@code match_id} field and sets it to the {@code match} bet field.
     *
     * @param list - the list of bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void setBetMatches(List<Bet> list) throws DAOException {
        for (Bet bet : list) {
            Match match = matchDao.findById(bet.getMatch_id());
            setTeams(match);
            bet.setMatch(match);
        }
    }


    /**
     * Finds a match by the {@code match_id} field and sets it to the {@code match} result field.
     *
     * @param list - the list of results.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void setResultMatches(List<Result> list) throws DAOException {
        if (!list.isEmpty()) {
            for (Result result : list) {
                Match match = matchDao.findById(result.getMatchId());
                setTeams(match);
                result.setMatch(match);
            }
        }
    }

}
