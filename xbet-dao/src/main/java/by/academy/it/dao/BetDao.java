package by.academy.it.dao;

import by.academy.it.entity.Bet;

import java.util.List;

/**
 * Defines {@code BetDao} methods.
 */
public interface BetDao {

    /**
     * Creates a new bet entry.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void create(Bet bet) throws DAOException;


    /**
     * Retrieves a bet by id.
     *
     * @param id the id of a bet.
     * @return {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public Bet getById(int id) throws DAOException ;


    /**
     * Retrieves a list of bets by user id.
     *
     * @param userId the id of a user.
     * @param startFrom position from which the select operation is performed.
     * @return {@code List<Bet>} - the list of user's bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Bet> findByUserId(int userId, int startFrom) throws DAOException;


    /**
     * Retrieves amount of user bets.
     *
     * @param userId the id of a user.
     * @return amount of user bets.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    int getAmountOfUserBets(int userId) throws DAOException;


    /**
     * Deletes a bet entry.
     *
     * @param id the {@link by.academy.it.entity.Bet} id.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void delete(int id) throws DAOException;


    /**
     * Updates a bet entry.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    public void update(Bet bet) throws DAOException;


    /**
     * Retrieves a list of bets entries by match id.
     *
     * @param matchId the id of a {@link by.academy.it.entity.Match} entity.
     * @return {@code List<Bet>} - the list of bets placed on this Match.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    List<Bet> findByMatchId(int matchId) throws DAOException;


    /**
     * Updates a bet entry.
     *
     * @param bet the {@link by.academy.it.entity.Bet} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    void updateStatus(Bet bet) throws DAOException;


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

}
