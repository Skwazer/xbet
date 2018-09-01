package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code MatchService} methods.
 */
public interface MatchService {

    /**
     * Retrieves a match id, finds match, puts it in the session and sends to user 'place bet' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void placeBet(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Retrieves a list of unplayed matches, puts it in the session and sends to 'matches' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void showUnplayedMatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;


    /**
     * Retrieves a list of unplayed matches, puts it in the session and sends to 'matches' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void showMatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;


    /**
     * Creates a match through {@link by.academy.it.dao.MatchDao}
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void createMatch(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Retrieves a match by id through {@link by.academy.it.dao.MatchDao} and sends a redirect to 'update match' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void showUpdateMatchPage(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Updates a match entry through {@link by.academy.it.dao.MatchDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void updateMatch(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Deletes a match entry through {@link by.academy.it.dao.MatchDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void deleteMatch(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
