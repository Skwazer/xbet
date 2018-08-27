package by.academy.it.service;

/*import by.academy.it.dao.RoleDao;
import by.academy.it.entity.Role;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;*/

public class RoleServiceTest {

    /*private static RoleService roleService;
    private static RoleDao roleDao;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session = mock(HttpSession.class);

    @Before
    public void setUp() throws Exception {
        roleDao = mock(RoleDao.class);
        roleService = RoleService.getInstance(roleDao);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void showRoles() throws Exception {
        when(roleDao.getRoles()).thenReturn(Collections.emptyList());
        when(request.getSession()).thenReturn(session);
        roleService.showRoles(request, response);
        verify(roleDao).getRoles();
    }

    @Test
    public void createRole() throws Exception {
        when(request.getParameter(anyString())).thenReturn("2");
        when(request.getSession()).thenReturn(session);
        roleService.createRole(request, response);
        verify(roleDao).create(any(Role.class));
    }

    @Test
    public void showUpdateRolePage() throws Exception {
        when(request.getParameter(anyString())).thenReturn("2");
        when(request.getSession()).thenReturn(session);
        roleService.showUpdateRolePage(request, response);
        verify(roleDao).findById(2);
    }

    @Test
    public void updateRole() throws Exception {
        when(request.getParameter(anyString())).thenReturn("2");
        when(request.getSession()).thenReturn(session);
        roleService.updateRole(request, response);
        verify(roleDao).update(any(Role.class));
    }

    @Test
    public void deleteRole() throws Exception {
        when(request.getParameter(anyString())).thenReturn("2");
        when(request.getSession()).thenReturn(session);
        roleService.deleteRole(request, response);
        verify(roleDao).delete(2);
    }*/

}