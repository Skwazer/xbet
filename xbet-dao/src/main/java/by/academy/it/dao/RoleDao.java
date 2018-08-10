package by.academy.it.dao;

import by.academy.it.entity.Role;

/**
 * Defines {@code RoleDao} methods. Extends {@link by.academy.it.dao.Dao} interface.
 */
public interface RoleDao extends Dao {

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
     * Deletes a role entry.
     *
     * @param role the {@link by.academy.it.entity.Role} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(Role role) throws DAOException;
}
