package by.academy.it.filter;

import by.academy.it.command.Constants;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter denies access to certain pages for unregistered users.
 */
@WebFilter(filterName = "UnregisteredUserFilter", urlPatterns = {"/main/bet", "/main/bets", "/main/topup"})
public class UnregisteredUserFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UnregisteredUserFilter.class);

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        if (user == null) {
            String URI = request.getRequestURI();
            logger.warn("Access to '" + URI + "' is denied");

            session.setAttribute(Constants.ERROR_MESSAGE, Constants.ACCESS_DENIED);
            response.sendRedirect(request.getContextPath() + Constants.MAIN_ERROR);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
