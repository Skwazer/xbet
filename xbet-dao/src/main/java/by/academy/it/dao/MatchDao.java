package by.academy.it.dao;

import by.academy.it.entity.Match;

import java.util.List;

/**
 * Defines {@code MatchDao} methods.
 */
public interface MatchDao {

    /**
     * Creates a new match entry.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(Match match) throws DAOException;


    /**
     * Retrieves a match entry by id.
     *
     * @param id the id of a match.
     * @return {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    Match findById(int id) throws DAOException;


    /**
     * Retrieves a list of unplayed matches.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Match>} - the list of unplayed matches.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Match> getUnplayedMatches(int startFrom) throws DAOException;


    /**
     * Retrieves amount of unplayed matches from the database.
     *
     * @return amount of unplayed matches.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfUnplayedMatches() throws DAOException;


    /**
     * Deletes a match entry.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(Match match) throws DAOException;

}
