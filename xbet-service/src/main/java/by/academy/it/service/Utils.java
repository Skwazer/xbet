package by.academy.it.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Util class, contains some useful methods.
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * Creates the referrer URI from a request.
     *
     * @param request {@code HttpServletRequest} request.
     * @return referrer URI.
     */
    static String getReferrerURI(HttpServletRequest request) {
        String URI = request.getSession().getAttribute(Constants.CURRENT_URI).toString();
        URI = URI.replaceFirst(request.getContextPath(), "");
        URI = URI.replaceFirst(Constants.PATH, "");
        URI = URI.replaceFirst(Constants.JSP, "");
        URI = request.getContextPath() + Constants.MAIN + URI;
        logger.info("referrer URI - " + URI);

        return URI;
    }


    /**
     * Creates the referrer path from a request.
     *
     * @param request {@code HttpServletRequest} request.
     * @return referrer path.
     */
    static String getReferrerPath(HttpServletRequest request) {
        String URI = request.getSession().getAttribute(Constants.CURRENT_URI).toString();
        String path = URI.replaceFirst(request.getContextPath(), "");
        logger.info("referrer path: " + path);

        return path;
    }


    /**
     * Checks if a string is valid.
     *
     * @param string a string to check.
     * @return {@code true} if a string is neither null nor empty, {@code false} otherwise.
     */
    static boolean isValidString(String string) {
        return string != null && !string.trim().isEmpty();
    }


    /**
     * Checks if a page parameter is valid.
     *
     * @param pageParam a string to check.
     * @return 1 if page parameter is null, 0 if a parameter is not a number or integer value of a page parameter otherwise.
     */
    static int checkPageParameter(String pageParam) {
        int page;
        if (pageParam == null) {
            page = 1;
        } else {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 0;
            }
        }
        return page;
    }


    /**
     * Calculates the position from which a select operation should start.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @return the position from which a select operation should start or -1 if the page is less than 1.
     * @throws IOException if an input or output error is detected.
     */
    static int calculateSelectStartPosition(int page, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int startFrom;
        if (page < 1) {
            logger.warn("Page parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PAGE_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);

            startFrom = -1;
        } else {
            startFrom =
                    page == 1 ? 0 : (page - 1) * 10;
        }
        return startFrom;
    }


}
