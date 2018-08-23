package by.academy.it.dao;

import by.academy.it.entity.Result;

import java.util.List;

/**
 * Defines {@code ResultDao} methods.
 */
public interface ResultDao {

    /**
     * Creates a new result entry.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(Result result) throws DAOException;


    /**
     * Retrieves a result entry by id.
     *
     * @param id the id of a result.
     * @return {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    Result findById(int id) throws DAOException;


    /**
     * Retrieves a list of all results.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Result>} - the list of all results.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Result> getResults(int startFrom) throws DAOException;


    /**
     * Retrieves amount of all results.
     *
     * @return amount of all results.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfAllResults() throws DAOException;


    /**
     * Deletes a result entry.
     *
     * @param id the {@link by.academy.it.entity.Result} id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(int id) throws DAOException;


    /**
     * Updates a result entry.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void update(Result result) throws DAOException;

}
