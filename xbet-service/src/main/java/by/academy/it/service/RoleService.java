package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code RoleService} methods.
 */
public interface RoleService {

    /**
     * Retrieves a list of roles through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void showRoles(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;


    /**
     * Creates a role entry through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void createRole(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Retrieves a role by id through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void showUpdateRolePage(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Updates a role entry through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void updateRole(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Deletes a role entry through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void deleteRole(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
