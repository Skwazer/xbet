package by.academy.it.service;

import by.academy.it.dao.DAOException;

import java.util.List;

/**
 * Defines {@code IdService} methods.
 */
public interface IdService {

    /**
     * Retrieves a list role entries' ids through {@link by.academy.it.dao.RoleDao}.
     *
     * @return a list of {@link by.academy.it.entity.Role} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Integer> getRolesIds() throws DAOException;


    /**
     * Retrieves a list of teams' ids through {@link by.academy.it.dao.TeamDao}.
     *
     * @return a list of {@link by.academy.it.entity.Team} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Integer> getTeamsIds() throws DAOException;


    /**
     * Retrieves a list of matches' ids through {@link by.academy.it.dao.MatchDao}.
     *
     * @return a list of {@link by.academy.it.entity.Match} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Integer> getMatchesIds() throws DAOException;

}
