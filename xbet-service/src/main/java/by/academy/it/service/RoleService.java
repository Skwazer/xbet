package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.RoleDao;
import by.academy.it.dao.factory.ConnectionPoolImpl;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class works with {@link by.academy.it.dao.RoleDao} class.
 *
 */
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    private static RoleService instance;
    private RoleDao roleDao;


    /**
     * Prohibits creating an instance of class outside the class.
     */
    private RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    /**
     * Creates {@code RoleService} instance if it is not created and returns it.
     *
     * @return {@code RoleService} instance.
     */
    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService(DaoFactory.getInstance(ConnectionPoolImpl.getInstance()).getRoleDao());
            logger.info("RoleService instance has been created");
        }
        return instance;
    }

    /**
     * Creates {@code RoleService} instance with a specified roleDao.
     *
     * @param roleDao data access object.
     * @return {@code RoleService} instance.
     */
    public static RoleService getInstance(RoleDao roleDao) {
        if (instance == null) {
            instance = new RoleService(roleDao);
            logger.info("RoleService instance with roleDao parameter has been created");
        }
        return instance;
    }


    /**
     * Retrieves a list of roles through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void showRoles(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Role> list;
        try {
            list = roleDao.getRoles();
            if (!list.isEmpty()) {
                logger.info("roles list has been retrieved");
                request.setAttribute(Constants.ROLES, list);
                request.getRequestDispatcher(Constants.PATH + Constants.GET + Constants.ROLES + Constants.JSP)
                        .forward(request, response);
            } else {
                logger.warn("Roles list is empty");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NO_ROLES_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } catch (DAOException e) {
            logger.error("RoleService cannot get a roles list", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLES_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Creates a role entry through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleParam = request.getParameter(Constants.ROLE);
        if (Utils.isValidString(roleParam)) {
            Role role = new Role();
            role.setRole(roleParam);
            try {
                roleDao.create(role);
                logger.info("role has been created");

                request.getSession().setAttribute(Constants.ROLE_MESSAGE, Constants.CREATE_ROLE_MESSAGE);
                response.sendRedirect( request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);

            } catch (DAOException e) {
                logger.error("RoleService cannot create a role", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Create role data are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_DATA_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a role by id through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void showUpdateRolePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleParam = request.getParameter(Constants.KEY);
        if (Utils.isValidString(roleParam)) {
            try {
                int id = Integer.parseInt(roleParam);
                Role role = roleDao.findById(id);
                if (role != null) {
                    request.getSession().setAttribute(Constants.UPDATED_ROLE, role);
                    logger.info("role has been retrieved");

                    response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.UPDATE_ROLE);

                } else {
                    logger.warn("Role with such id has not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_NOT_FOUND);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } catch (Exception e) {
                logger.error("An exception occurred during show update role page operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_ROLE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Show update role page operation parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_ROLE_PARAMETER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates a role entry through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void updateRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter(Constants.ID);
        String roleParam = request.getParameter(Constants.ROLE);
        try {
            int id = Integer.parseInt(idParam);
            if (id == 1) {
                logger.warn("Attempt to update admin role");
                request.getSession().setAttribute(Constants.ROLE_MESSAGE, Constants.UPDATE_FORBIDDEN);
            } else {
                Role role = new Role();
                role.setId(id);
                role.setRole(roleParam);
                roleDao.update(role);

                logger.info("role has been updated");
                request.getSession().setAttribute(Constants.ROLE_MESSAGE, Constants.UPDATE_ROLE_MESSAGE);
            }
            response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);

        } catch (Exception e) {
            logger.error("An exception occurred during update role operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_ROLE_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Deletes a role entry through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void deleteRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        try {
            int id = Integer.parseInt(key);

            if (id == 1) {
                logger.warn("Attempt to delete admin role");
                request.getSession().setAttribute(Constants.ROLE_MESSAGE, Constants.DELETE_FORBIDDEN);
            } else {
                roleDao.delete(id);
                logger.info("role has been deleted");
                request.getSession().setAttribute(Constants.ROLE_MESSAGE, Constants.DELETE_ROLE_MESSAGE);
            }
            response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);

        } catch (DAOException e) {
            logger.error("An exception occurred during delete role operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_ROLE_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }

}
