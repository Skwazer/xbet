package by.academy.it.dao;

import by.academy.it.entity.Bet;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class BetDaoImplTest {

    private static BetDao betDao;
    private static Bet bet;

    @Before
    public void setUp() {
        betDao = mock(BetDao.class);
        bet = mock(Bet.class);
    }

    @Test
    public void create() throws Exception {
        betDao.create(bet);
        verify(betDao).create(bet);
    }

    @Test
    public void getById() throws Exception {
        when(betDao.getById(1)).thenReturn(bet);
        assertEquals(betDao.getById(1), bet);
        verify(betDao).getById(1);
    }

    @Test
    public void findByUserId() throws Exception {
        assertEquals(betDao.findByUserId(1, 1), Collections.emptyList());
        verify(betDao).findByUserId(1, 1);
    }

    @Test
    public void getAmountOfUserBets() throws Exception {
        when(betDao.getAmountOfUserBets(1)).thenReturn(1);
        assertEquals(betDao.getAmountOfUserBets(1), 1);
        verify(betDao).getAmountOfUserBets(1);
    }

    @Test
    public void delete() throws Exception {
        betDao.delete(1);
        verify(betDao).delete(1);
    }

    @Test
    public void update() throws Exception {
        betDao.update(bet);
        verify(betDao).update(bet);
    }

    @Test
    public void findByMatchId() throws Exception {
        assertEquals(betDao.findByMatchId(1), Collections.emptyList());
        verify(betDao).findByMatchId(1);
    }

    @Test
    public void updateStatus() throws Exception {
        betDao.updateStatus(bet);
        verify(betDao).updateStatus(bet);
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(betDao.findAll(1), Collections.emptyList());
        verify(betDao).findAll(1);
    }

    @Test
    public void getAmountOfAllBets() throws Exception {
        when(betDao.getAmountOfAllBets()).thenReturn(10);
        assertEquals(betDao.getAmountOfAllBets(), 10);
    }

}