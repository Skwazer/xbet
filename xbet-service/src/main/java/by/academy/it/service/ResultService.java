package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code ResultService} methods.
 */
public interface ResultService {

    /**
     * Retrieves a list of all results, puts it in the session and sends to 'results' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void showResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


    /**
     * Retrieves a list of last results, puts it in the session and sends to 'results' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void showLastResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


    /**
     * Retrieves a result by id through {@link by.academy.it.dao.ResultDao} and sends a redirect to 'update result' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void showUpdateResultPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;


    /**
     * Retrieves a list of results' ids and sends forward to 'create result' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void showCreateResultPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;


    /**
     * Updates a result entry through {@link by.academy.it.dao.ResultDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void updateResult(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Deletes a result entry through {@link by.academy.it.dao.ResultDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void deleteResult(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
