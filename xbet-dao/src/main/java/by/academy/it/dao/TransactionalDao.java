package by.academy.it.dao;

import by.academy.it.entity.Bet;
import by.academy.it.entity.Result;

import java.util.List;

/**
 * Defines {@code TransactionalDao} methods.
 */
public interface TransactionalDao {

    /**
     * Updates user's balance and creates a bet entry.
     *
     * @param login the user login.
     * @param balance the user balance.
     * @param bet {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void placeBet(String login, double balance, Bet bet) throws DAOException;


    /**
     * Creates a result entry, updates bet entries status and balances of winning users.
     *
     * @param result {@link by.academy.it.entity.Result} - the result of a match.
     * @param bets {@code List<Bet>} - bets placed on the match.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void finishMatch(Result result, List<Bet> bets) throws DAOException;

}
