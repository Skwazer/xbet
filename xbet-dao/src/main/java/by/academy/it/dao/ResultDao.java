package by.academy.it.dao;

import by.academy.it.entity.Result;

/**
 * Defines {@code ResultDao} methods. Extends {@link by.academy.it.dao.Dao} interface.
 */
public interface ResultDao extends Dao {

    /**
     * Creates a new result entry.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(Result result) throws DAOException;


    /**
     * Retrieves a result entry.
     *
     * @param matchId the id of a match.
     * @return {@link by.academy.it.entity.Result} entity by match id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    Result findByMatchId(int matchId) throws DAOException;


    /**
     * Deletes a result entry.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(Result result) throws DAOException;
}
