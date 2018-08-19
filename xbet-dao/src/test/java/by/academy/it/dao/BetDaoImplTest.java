package by.academy.it.dao;

import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Bet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class BetDaoImplTest {

    BetDao betDao = DaoFactory.getInstance().getBetDao();

    @Test
    public void create() throws Exception {
        Bet bet = new Bet();
        bet.setUser_id(1);
        bet.setMatch_id(1);
        bet.setBetResult("1X");
        bet.setBet(1.31);
        bet.setMoney(100500d);
        bet.setStatus("test_status");
        betDao.create(bet);
        assertFalse(betDao.findByUserId(1, 0).isEmpty());
    }

    @Test
    public void findByUserId() throws Exception {
        assertFalse(betDao.findByUserId(2, 0).isEmpty());
    }

    @Test
    public void findByMatchId() throws Exception {
        assertFalse(betDao.findByMatchId(9).isEmpty());
    }

    @Test
    public void delete() throws Exception {
        Bet bet = new Bet();
        bet.setMatch_id(1);
        betDao.delete(bet);
    }

}