package by.academy.it.dao;

import by.academy.it.entity.Team;

import java.util.List;

/**
 * Defines {@code TeamDao} methods.
 */
public interface TeamDao {


    /**
     * Creates a new team entry.
     *
     * @param team the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(Team team) throws DAOException;


    /**
     * Retrieves a list of all team entries.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Team>} - the list of all teams.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Team> getTeams(int startFrom) throws DAOException;


    /**
     * Retrieves a list of teams' ids.
     *
     * @return {@code List<Integer>} - the list of teams' ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Integer> getIds() throws DAOException;


    /**
     * Retrieves amount of all teams.
     *
     * @return amount of all teams.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfAllTeams() throws DAOException;


    /**
     * Retrieves a team entry by id.
     *
     * @param id the id of a team.
     * @return the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    Team findById(int id) throws DAOException;


    /**
     * Updates a team entry.
     *
     * @param team the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void update(Team team) throws DAOException;


    /**
     * Deletes a team entry.
     *
     * @param id the {@link by.academy.it.entity.Team} id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(int id) throws DAOException;

}
