package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code FinishMatchService} methods.
 */
public interface TransactionalService {

    /**
     * Withdraws user's money and creates a bet entry.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void confirmBet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Creates a result entity and finishes the match.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void createResult(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * Creates a random result entity to the selected match and finishes the match.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void createRandomResult(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
