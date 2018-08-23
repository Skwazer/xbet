package by.academy.it.dao;

import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Team;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TeamDaoImplTest {

    private TeamDao teamDao = DaoFactory.getInstance().getTeamDao();

    @Test
    public void create() throws Exception {
        Team team = new Team();
        team.setName("test_team");
        teamDao.create(team);
        Team team1 = teamDao.findById(17);
        assertNotNull(team1);
    }

    @Test
    public void findById() throws Exception {
        Team team = teamDao.findById(10);
        assertEquals("Russia", team.getName());
    }


}