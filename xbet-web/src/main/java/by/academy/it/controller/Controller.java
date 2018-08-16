package by.academy.it.controller;

import by.academy.it.command.Command;
import by.academy.it.command.CommandFactory;
import by.academy.it.command.Constants;
import by.academy.it.command.NoActionCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller for all requests.
 */
public class Controller extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final CommandFactory FACTORY = CommandFactory.getInstance();


    /**
     * Handles {@code GET} requests.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    /**
     * Handles {@code POST} requests.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Command command = FACTORY.getCommand(request);
        try {
            logger.info("command for this request is " + command.getClass().getSimpleName());
            command.execute(request, response);
        } catch (Exception e) {
            logger.error("Command execution is failed", e);
            response.sendRedirect(request.getContextPath() + Constants.MAIN_ERROR);
        }
    }

}
