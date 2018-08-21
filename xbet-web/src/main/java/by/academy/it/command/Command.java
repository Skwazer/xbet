package by.academy.it.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This interface is a root interface for commands.
 */
public interface Command {

    /**
     * Abstract method. Commands must provide an implementation.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
