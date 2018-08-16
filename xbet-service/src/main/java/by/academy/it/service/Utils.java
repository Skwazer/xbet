package by.academy.it.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

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
}
