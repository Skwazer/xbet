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
     * Creates a random result to selected match, finds all active bets, placed on this match, sets a bet status to
     * 'won' or 'lost' and updates the user balance.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void finishMatch(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
