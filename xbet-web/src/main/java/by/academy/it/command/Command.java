package by.academy.it.command;

import by.academy.it.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract class for all commands.
 */
public abstract class Command {

    private static final Logger logger = LoggerFactory.getLogger(Command.class);
    static ServiceFactory serviceFactory;

    /**
     * Sets the value of the serviceFactory field.
     *
     * @param factory {@link by.academy.it.service.ServiceFactory}
     */
    public static void setServiceFactory(ServiceFactory factory) {
        serviceFactory = factory;
        logger.info("ServiceFactory has been set");
    }

    /**
     * Abstract method. Commands must provide an implementation.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public abstract void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

}
