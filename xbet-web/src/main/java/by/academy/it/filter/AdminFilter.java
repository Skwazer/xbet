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
 * This filter denies access to management pages for users without admin rights.
 */
@WebFilter(filterName = "AdminFilter",
        urlPatterns = {"/main/create/*", "/main/get/*", "/main/update/*", "/main/delete/*", "/main/change/*"})

public class AdminFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        if (user != null && user.getRole() == 1) {
            chain.doFilter(req, resp);
        } else {
            String URI = request.getRequestURI();
            logger.warn("Access to '" + URI + "' is denied");

            session.setAttribute(Constants.ERROR_MESSAGE, Constants.ACCESS_ADMIN);
            response.sendRedirect(request.getContextPath() + Constants.MAIN_ERROR);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
