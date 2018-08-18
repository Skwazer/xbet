package by.academy.it.dao;

import by.academy.it.entity.Team;

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
     * Retrieves a team entry by id.
     *
     * @param id the id of a team.
     * @return the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    Team findById(int id) throws DAOException;


    /**
     * Deletes a team entry.
     *
     * @param team the {@link by.academy.it.entity.Team} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(Team team) throws DAOException;

}
