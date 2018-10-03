package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.MatchDao;
import by.academy.it.dao.RoleDao;
import by.academy.it.dao.TeamDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class retrieves entries' ids from the database.
 *
 */
class IdServiceImpl implements IdService {

    private static final Logger logger = LoggerFactory.getLogger(IdServiceImpl.class);
    private RoleDao roleDao;
    private TeamDao teamDao;
    private MatchDao matchDao;

    /**
     * Constructs an instance of the {@code IdService}.
     *
     * @param roleDao a RoleDao instance.
     * @param teamDao a TeamDao instance.
     * @param matchDao a MatchDao instance.
     */
    IdServiceImpl(RoleDao roleDao, TeamDao teamDao, MatchDao matchDao) {
        this.roleDao = roleDao;
        this.teamDao = teamDao;
        this.matchDao = matchDao;
        logger.info("IdService has been created");
    }


    /**
     * Retrieves a list of roles' ids through {@link by.academy.it.dao.RoleDao}.
     *
     * @return a list of {@link by.academy.it.entity.Role} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Integer> getRolesIds() throws DAOException {
        return roleDao.getIds();
    }


    /**
     * Retrieves a list of teams' ids through {@link by.academy.it.dao.TeamDao}.
     *
     * @return a list of {@link by.academy.it.entity.Team} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Integer> getTeamsIds() throws DAOException {
        return teamDao.getIds();
    }


    /**
     * Retrieves a list of matches' ids through {@link by.academy.it.dao.MatchDao}.
     *
     * @return a list of {@link by.academy.it.entity.Match} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public List<Integer> getMatchesIds() throws DAOException {
        return matchDao.getIds();
    }

}
