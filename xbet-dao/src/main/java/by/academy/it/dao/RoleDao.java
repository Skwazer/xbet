package by.academy.it.dao;

import by.academy.it.entity.Role;

import java.util.List;

/**
 * Defines {@code RoleDao} methods.
 */
public interface RoleDao {

    /**
     * Creates a new role entry.
     *
     * @param role the {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(Role role) throws DAOException;


    /**
     * Retrieves a role entry by id.
     *
     * @param id the id of a role.
     * @return {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    Role findById(int id) throws DAOException;


    /**
     * Retrieves a list of role entries.
     *
     * @return list of {@link by.academy.it.entity.Role} entities.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Role> getRoles() throws DAOException;


    /**
     * Retrieves a list role entries' ids.
     *
     * @return list of {@link by.academy.it.entity.Role} ids.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Integer> getIds() throws DAOException;


    /**
     * Updates a role entry.
     *
     * @param role the {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void update(Role role) throws DAOException;


    /**
     * Deletes a role entry.
     *
     * @param id - role id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(int id) throws DAOException;

}
