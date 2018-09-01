package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code TeamService} methods.
 */
public interface TeamService {

    /**
     * Retrieves a list of teams through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void showTeams(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;


    /**
     * Creates a team entry through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void createTeam(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Retrieves a team by id through {@link by.academy.it.dao.RoleDao} and sends a redirect to the 'update team' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void showUpdateTeamPage(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Updates a team entry through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void updateTeam(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Deletes a team entry through {@link by.academy.it.dao.TeamDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void deleteTeam(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
