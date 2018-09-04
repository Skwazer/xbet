package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.TeamDao;
import by.academy.it.entity.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class TeamServiceImplTest {

    private static Team team;
    private static TeamDao teamDao;
    private static TeamService teamService;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static RequestDispatcher dispatcher;
    private static DAOException DAOExc;

    @BeforeAll
    static void setup() {
        team = new Team();
        teamDao = mock(TeamDao.class);
        teamService = new TeamServiceImpl(teamDao);
        request = mock(HttpServletRequest.class);
        dispatcher = mock(RequestDispatcher.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        DAOExc = new DAOException(Constants.DAO_TEST_EXC);
    }

    @BeforeEach
    void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @AfterEach
    void resetMocks() {
        reset(teamDao, request, response, dispatcher, session);
    }

    @Test
    void showTeams1() throws Exception {
        List<Team> list = new ArrayList<>();
        list.add(team);
        when(teamDao.getTeams(0)).thenReturn(list);
        when(request.getRequestDispatcher(Constants.PATH + Constants.GET_TEAMS + Constants.JSP))
                .thenReturn(dispatcher);

        teamService.showTeams(request, response);
        verify(request).getParameter(Constants.PAGE);
        verify(teamDao, times(1)).getTeams(0);
        verify(teamDao, times(1)).getAmountOfAllTeams();
        verify(request).setAttribute(Constants.TEAMS_LIST, list);
        verify(request).setAttribute(Constants.CURRENT_PAGE, 1);
        verify(request).setAttribute(Constants.PAGES, 0d);
        verify(request).getRequestDispatcher(Constants.PATH + Constants.GET_TEAMS + Constants.JSP);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void showTeams2() throws Exception {
        List<Team> list = Collections.emptyList();
        when(teamDao.getTeams(0)).thenReturn(list);
        when(request.getRequestDispatcher(Constants.PATH + Constants.GET_TEAMS + Constants.JSP))
                .thenReturn(dispatcher);

        teamService.showTeams(request, response);
        verify(request).getParameter(Constants.PAGE);
        verify(teamDao, times(1)).getTeams(0);
        verify(teamDao, times(1)).getAmountOfAllTeams();
        verify(request).setAttribute(Constants.TEAMS_MESSAGE, Constants.TEAMS_LIST_EMPTY);
        verify(request).getRequestDispatcher(Constants.PATH + Constants.GET_TEAMS + Constants.JSP);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void showTeams3() throws Exception {
        when(teamDao.getTeams(0)).thenThrow(DAOExc);

        teamService.showTeams(request, response);
        verify(request).getParameter(Constants.PAGE);
        verify(teamDao, times(1)).getTeams(0);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.TEAMS_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void createTeam1() throws Exception {
        when(request.getParameter(Constants.NAME)).thenReturn(Constants.TEST);

        teamService.createTeam(request, response);
        verify(request).getParameter(Constants.NAME);
        verify(teamDao, times(1)).create(any(Team.class));
        verify(request).getSession();
        verify(session).setAttribute(Constants.TEAMS_MESSAGE, Constants.CREATE_TEAM_MESSAGE);
        verify(response).sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_TEAMS);
    }

    @Test
    void createTeam2() throws Exception {
        teamService.createTeam(request, response);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.TEAM_DATA_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void createTeam3() throws Exception {
        when(request.getParameter(Constants.NAME)).thenReturn(Constants.TEST);
        doThrow(DAOExc).when(teamDao).create(any(Team.class));

        teamService.createTeam(request, response);
        verify(request).getParameter(Constants.NAME);
        verify(teamDao, times(1)).create(any(Team.class));
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.TEAM_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void showUpdateTeamPage1() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn(Constants.STRING_1);
        when(teamDao.findById(1)).thenReturn(team);

        teamService.showUpdateTeamPage(request, response);
        verify(request).getParameter(Constants.KEY);
        verify(teamDao, times(1)).findById(1);
        verify(request).getSession();
        verify(session).setAttribute(Constants.UPDATED_TEAM, team);
        verify(response).sendRedirect(request.getContextPath() + Constants.MAIN + Constants.UPDATE_TEAM);
    }

    @Test
    void showUpdateTeamPage2() throws Exception {
        teamService.showUpdateTeamPage(request, response);
        verify(request).getParameter(Constants.KEY);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_BET_PARAMETER_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void showUpdateTeamPage3() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn(Constants.STRING_1);
        when(teamDao.findById(1)).thenThrow(DAOExc);

        teamService.showUpdateTeamPage(request, response);
        verify(request).getParameter(Constants.KEY);
        verify(teamDao, times(1)).findById(1);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_TEAM_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void updateTeam1() throws Exception {
        when(request.getParameter(Constants.ID)).thenReturn(Constants.STRING_1);
        when(request.getParameter(Constants.NAME)).thenReturn(Constants.TEST);

        teamService.updateTeam(request, response);
        verify(request).getParameter(Constants.ID);
        verify(request).getParameter(Constants.NAME);
        verify(teamDao, times(1)).update(any(Team.class));
        verify(request).getSession();
        verify(session).setAttribute(Constants.TEAMS_MESSAGE, Constants.UPDATE_TEAM_MESSAGE);
        verify(response).sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_TEAMS);
    }

    @Test
    void updateTeam2() throws Exception {
        teamService.updateTeam(request, response);
        verify(request).getParameter(Constants.ID);
        verify(request).getParameter(Constants.NAME);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void updateTeam3() throws Exception {
        when(request.getParameter(Constants.ID)).thenReturn(Constants.STRING_1);
        when(request.getParameter(Constants.NAME)).thenReturn(Constants.TEST);
        doThrow(DAOExc).when(teamDao).update(any(Team.class));

        teamService.updateTeam(request, response);
        verify(request).getParameter(Constants.ID);
        verify(request).getParameter(Constants.NAME);
        verify(teamDao, times(1)).update(any(Team.class));
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_TEAM_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void deleteTeam1() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn(Constants.STRING_1);

        teamService.deleteTeam(request, response);
        verify(request).getParameter(Constants.KEY);
        verify(teamDao, times(1)).delete(1);
        verify(request).getSession();
        verify(session).setAttribute(Constants.TEAMS_MESSAGE, Constants.DELETE_TEAM_MESSAGE);
        verify(response).sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET_TEAMS);
    }

    @Test
    void deleteTeam2() throws Exception {
        teamService.deleteTeam(request, response);
        verify(request).getParameter(Constants.KEY);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

    @Test
    void deleteTeam3() throws Exception {
        when(request.getParameter(Constants.KEY)).thenReturn(Constants.STRING_1);
        doThrow(DAOExc).when(teamDao).delete(1);

        teamService.deleteTeam(request, response);
        verify(request).getParameter(Constants.KEY);
        verify(teamDao, times(1)).delete(1);
        verify(request).getSession();
        verify(session).setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_TEAM_ERROR);
        verify(response).sendRedirect(request.getContextPath() + Constants.ERROR);
    }

}