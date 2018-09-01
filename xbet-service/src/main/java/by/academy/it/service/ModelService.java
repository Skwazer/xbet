package by.academy.it.service;

import by.academy.it.entity.Bet;
import by.academy.it.entity.Match;
import by.academy.it.entity.Result;

import java.util.List;

/**
 * Defines {@code ModelService} methods.
 */
public interface ModelService {

    /**
     * Finds teams by {@code team1_id} and {@code team2_id} fields
     * and sets them to {@code team1} and {@code team2} match fields.
     *
     * @param match the {@link by.academy.it.entity.Match} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    void setTeams(Match match) throws ServiceException;


    /**
     * Finds a match by the {@code match_id} field and sets it to the {@code match} bet field.
     *
     * @param list - the list of bets.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    void setBetMatches(List<Bet> list) throws ServiceException;


    /**
     * Finds a match by the {@code match_id} field and sets it to the {@code match} result field.
     *
     * @param list - the list of results.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    void setResultMatches(List<Result> list) throws ServiceException;

}
