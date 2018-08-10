package by.academy.it.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

/**
 * This filter gets a locale from the request, and sets it into the application context.
 */
public class LocaleFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        Locale locale = request.getLocale();
        Config.set(req.getSession(), Config.FMT_LOCALE, locale);
        logger.info("locale has been set - "  + locale);

        chain.doFilter(req, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
