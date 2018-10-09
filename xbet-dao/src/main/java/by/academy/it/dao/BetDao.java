package by.academy.it.dao;

import by.academy.it.entity.Bet;

import java.util.List;

/**
 * Defines {@code BetDao} methods.
 */
public interface BetDao {

    /**
     * Retrieves a list of active bets by user id.
     *
     * @param userId the id of a user.
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Bet>} - the list of user's bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Bet> findActiveBetsByUserId(int userId, int startFrom) throws DAOException;

    /**
     * Retrieves a list of played bets by user id.
     *
     * @param userId the id of a user.
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Bet>} - the list of user's bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Bet> findPlayedBetsByUserId(int userId, int startFrom) throws DAOException;

    /**
     * Retrieves amount of active user bets.
     *
     * @param userId the id of a user.
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfActiveUserBets(int userId) throws DAOException;

    /**
     * Retrieves amount of played user bets.
     *
     * @param userId the id of a user.
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfPlayedUserBets(int userId) throws DAOException;

    /**
     * Retrieves a list of bets entries by match id.
     *
     * @param matchId the id of a {@link by.academy.it.entity.Match} entity.
     * @return {@code List<Bet>} - the list of bets placed on this Match.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Bet> findByMatchId(int matchId) throws DAOException;


    /**
     * Retrieves a list of all bets.
     *
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Bet>} - the list of all bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Bet> findAll(int startFrom) throws DAOException;


    /**
     * Retrieves amount of all bets.
     *
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfAllBets() throws DAOException;


    /**
     * Deletes played user bets.
     *
     * @param userId the id of a user.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void deletePlayedBets(Integer userId) throws DAOException;

}
