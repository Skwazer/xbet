package by.academy.it.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines {@code UserService} methods.
 */
public interface UserService {

    /**
     * Authenticates user's login.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void authenticateLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;


    /**
     * Checks if login is free.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void checkNewUserLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;


    /**
     * Deletes a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Retrieves a list of users through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void showUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


    /**
     * Creates a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Retrieves a user by id through {@link by.academy.it.dao.UserDao} and sends a redirect to 'update user' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void showUpdateUserPage(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Updates a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Sets the locale of the application.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void changeLocale(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Checks if the user has enough money to place a bet.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    void checkBalance(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;


    /**
     * Checks if the user's data are located in the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void checkIsUserLoggedIn(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;


    /**
     * Puts into the session data about logged user.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Tops up the user's balance.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void topup(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Creates a new user in the database and puts user's data in the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * Deletes user's data from the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
