package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.RoleDao;
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
class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private RoleDao roleDao;

    /**
     * Constructs an instance of the {@code RoleService}.
     *
     * @param roleDao a RoleDao instance.
     */
    RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
        logger.info("RoleService has been created");
    }


    /**
     * Retrieves a list of roles through {@link by.academy.it.dao.RoleDao} and sends forward to roles page.
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
        if (Utils.isStringValid(roleParam) && isRoleValid(roleParam)) {
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
     * Validates role value.
     *
     * @param role a value to validate.
     * @return true if role matches the regular expression or false otherwise.
     */
    private boolean isRoleValid(String role) {
        return role.matches(Constants.ROLE_REGEX);
    }


    /**
     * Retrieves a role by id through {@link by.academy.it.dao.RoleDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showUpdateRolePage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String roleParam = request.getParameter(Constants.KEY);
        if (Utils.isStringValid(roleParam)) {
            try {
                int id = Integer.parseInt(roleParam);
                Role role = roleDao.findById(id);
                if (role != null) {
                    request.setAttribute(Constants.UPDATED_ROLE, role);
                    logger.info("role has been retrieved");

                    request.getRequestDispatcher(Constants.PATH + Constants.UPDATE_ROLE + Constants.JSP)
                            .forward(request, response);

                } else {
                    logger.warn("Role with such id has not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_NOT_FOUND);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
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
        if (Utils.areStringsValid(idParam, roleParam) && isRoleValid(roleParam)) {
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

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during update role operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_ROLE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Update role parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

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
        if (Utils.isStringValid(key)) {
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

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during delete role operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_ROLE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Delete role parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }

}
