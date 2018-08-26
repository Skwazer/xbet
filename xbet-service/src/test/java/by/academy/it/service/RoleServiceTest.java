package by.academy.it.service;

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