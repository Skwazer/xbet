package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.RoleDao;
import by.academy.it.entity.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    private static RoleService roleService;
    private static RoleDao roleDao;
    private static Role role;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static RequestDispatcher dispatcher;
    private static DAOException DAOExc;

    @BeforeAll
    public static void setup() {
        roleDao = mock(RoleDao.class);
        role = mock(Role.class);
        roleService = RoleService.getInstance(roleDao);
        request = mock(HttpServletRequest.class);
        dispatcher = mock(RequestDispatcher.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        DAOExc = new DAOException("dao test exception");

        when(request.getParameter(anyString())).thenReturn("2");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void showRoles1() throws Exception {
        List<Role> list = new ArrayList<>();
        list.add(role);
        when(roleDao.getRoles()).thenReturn(list);
        when(request.getRequestDispatcher(Constants.PATH + Constants.GET + Constants.ROLES + Constants.JSP))
                .thenReturn(dispatcher);
        roleService.showRoles(request, response);
        verify(roleDao).getRoles();
        verify(request).setAttribute(Constants.ROLES, list);
        verify(request).getRequestDispatcher(Constants.PATH + Constants.GET + Constants.ROLES + Constants.JSP);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void showRoles2() throws Exception {
        List<Role> list = Collections.emptyList();
        when(roleDao.getRoles()).thenReturn(list);
        roleService.showRoles(request, response);
        verify(roleDao, atLeastOnce()).getRoles();
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.NO_ROLES_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    public void showRoles3() throws Exception {
        when(roleDao.getRoles()).thenThrow(DAOExc);
        roleService.showRoles(request, response);
        verify(roleDao, atLeastOnce()).getRoles();
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.ROLES_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }


    @Test
    public void createRole1() throws Exception {
        roleService.createRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.ROLE);
        verify(roleDao).create(any(Role.class));
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ROLE_MESSAGE, Constants.CREATE_ROLE_MESSAGE);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);
    }

    @Test
    public void createRole2() throws Exception {
        when(request.getParameter(Constants.ROLE)).thenReturn(null);
        roleService.createRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.ROLE);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_DATA_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    public void createRole3() throws Exception {
        when(request.getParameter(Constants.ROLE)).thenReturn("test");
        doThrow(DAOExc).when(roleDao).create(any(Role.class));
        roleService.createRole(request, response);
        verify(roleDao, atLeastOnce()).create(any(Role.class));
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }


    @Test
    public void showUpdateRolePage1() throws Exception {
        when(roleDao.findById(2)).thenReturn(role);
        roleService.showUpdateRolePage(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(roleDao).findById(2);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.UPDATED_ROLE, role);
        verify(response)
                .sendRedirect(request.getContextPath() + Constants.MAIN + Constants.UPDATE_ROLE);
    }

    @Test
    public void showUpdateRolePage2() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn(null);
        roleService.showUpdateRolePage(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_ROLE_PARAMETER_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    public void showUpdateRolePage3() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn("2");
        when(roleDao.findById(2)).thenReturn(null);
        roleService.showUpdateRolePage(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(roleDao, atLeastOnce()).findById(2);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.ROLE_NOT_FOUND);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    public void showUpdateRolePage4() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn("2");
        when(roleDao.findById(2)).thenThrow(DAOExc);
        roleService.showUpdateRolePage(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(roleDao, atLeastOnce()).findById(2);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_ROLE_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }


    @Test
    public void updateRole1() throws Exception {
        roleService.updateRole(request, response);
        verify(request).getParameter(Constants.ID);
        verify(request, atLeastOnce()).getParameter(Constants.ROLE);
        verify(roleDao).update(any(Role.class));
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ROLE_MESSAGE, Constants.UPDATE_ROLE_MESSAGE);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);
    }

    @Test
    public void updateRole2() throws Exception {
        when(request.getParameter(Constants.ID)).thenReturn("1");
        roleService.updateRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.ID);
        verify(request, atLeastOnce()).getParameter(Constants.ROLE);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ROLE_MESSAGE, Constants.UPDATE_FORBIDDEN);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);
    }

    @Test
    public void updateRole3() throws Exception {
        when(request.getParameter(Constants.ID)).thenReturn("2");
        doThrow(DAOExc).when(roleDao).update(any(Role.class));
        roleService.updateRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.ID);
        verify(request, atLeastOnce()).getParameter(Constants.ROLE);
        verify(roleDao, atLeastOnce()).update(any(Role.class));
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_ROLE_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }


    @Test
    public void deleteRole1() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn("2");
        roleService.deleteRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(roleDao).delete(2);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ROLE_MESSAGE, Constants.DELETE_ROLE_MESSAGE);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);
    }


    @Test
    public void deleteRole2() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn("1");
        roleService.deleteRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ROLE_MESSAGE, Constants.DELETE_FORBIDDEN);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.ROLES);
    }

    @Test
    public void deleteRole3() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn("2");
        doThrow(DAOExc).when(roleDao).delete(2);
        roleService.deleteRole(request, response);
        verify(request, atLeastOnce()).getParameter(Constants.KEY);
        verify(roleDao, atLeastOnce()).delete(2);
        verify(request, atLeastOnce()).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_ROLE_ERROR);
        verify(response, atLeastOnce())
                .sendRedirect(request.getContextPath() + Constants.ERROR);
    }

}