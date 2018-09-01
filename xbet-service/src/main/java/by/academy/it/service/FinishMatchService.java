package by.academy.it.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code FinishMatchService} methods.
 */
public interface FinishMatchService {

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
