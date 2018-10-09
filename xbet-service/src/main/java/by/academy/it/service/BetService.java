package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code BetService} methods.
 */
public interface BetService {

    /**
     * Retrieves a list of the user's bets and sends it to 'bets page'.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void showUserBets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;


    /**
     * Retrieves a list of all bets and sends it to 'bets page'.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void showAllBets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Deletes played user's bets.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void deletePlayedBets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

}
