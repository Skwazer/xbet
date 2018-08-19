package by.academy.it.service;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MatchServiceTest {

    private MatchService matchService = MatchService.getInstance();

    @Test
    public void getMatches() throws Exception {
        assertNotNull(matchService.getUnplayedMatches(1));
    }

    @Test
    public void getMatchById() throws Exception {
        assertNotNull(matchService.getMatchById(1));
    }

}