package by.academy.it.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

/**
 * This filter sets the locale of the application.
 */
public class LocaleFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);

    /**
     * Gets a locale from the request, and sets it into the application context.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @param chain {@code FilterChain} chain.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        Locale locale = request.getLocale();
        Config.set(req.getSession(), Config.FMT_LOCALE, locale);
        logger.info("locale has been set - "  + locale);

        chain.doFilter(req, response);
    }

    public void destroy() {

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
