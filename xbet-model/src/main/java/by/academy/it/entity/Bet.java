package by.academy.it.entity;

import java.io.Serializable;

/**
 * Representation of database table 'bets'.
 */
public class Bet implements Serializable {

    private static final long serialVersionUID = -8902292860220708529L;

    private Integer id;
    private Integer user_id;
    private Integer match_id;
    private String betResult;
    private Double bet;
    private Double money;
    private String status;
    private Match match;

    /**
     * The {@code betResult} field getter.
     * @return a value of the {@code betResult} field.
     */
    public String getBetResult() {
        return betResult;
    }

    /**
     * The {@code betResult} field setter.
     * @param betResult value to set.
     */
    public void setBetResult(String betResult) {
        this.betResult = betResult;
    }

    /**
     * The {@code match} field getter.
     * @return a value of the {@code match} field.
     */
    public Match getMatch() {
        return match;
    }

    /**
     * The {@code match} field setter.
     * @param match value to set.
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * The {@code id} field getter.
     * @return a value of the {@code id} field.
     */
    public Integer getId() {
        return id;
    }

    /**
     * The {@code id} field setter.
     * @param id value to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The {@code user_id} field getter.
     * @return a value of the {@code user_id} field.
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * The {@code user_id} field setter.
     * @param user_id value to set.
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * The {@code match_id} field getter.
     * @return a value of the {@code match_id} field.
     */
    public Integer getMatch_id() {
        return match_id;
    }

    /**
     * The {@code match_id} field setter.
     * @param match_id value to set.
     */
    public void setMatch_id(Integer match_id) {
        this.match_id = match_id;
    }

    /**
     * The {@code bet} field getter.
     * @return a value of the {@code bet} field.
     */
    public Double getBet() {
        return bet;
    }

    /**
     * The {@code bet} field setter.
     * @param bet value to set.
     */
    public void setBet(Double bet) {
        this.bet = bet;
    }

    /**
     * The {@code money} field getter.
     * @return a value of the {@code money} field.
     */
    public Double getMoney() {
        return money;
    }

    /**
     * The {@code money} field setter.
     * @param money value to set.
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * The {@code status} field getter.
     * @return a value of the {@code status} field.
     */
    public String getStatus() {
        return status;
    }

    /**
     * The {@code status} field setter.
     * @param status value to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bet bet1 = (Bet) o;

        if (id != null ? !id.equals(bet1.id) : bet1.id != null) return false;
        if (user_id != null ? !user_id.equals(bet1.user_id) : bet1.user_id != null) return false;
        if (match_id != null ? !match_id.equals(bet1.match_id) : bet1.match_id != null) return false;
        if (betResult != null ? !betResult.equals(bet1.betResult) : bet1.betResult != null) return false;
        if (bet != null ? !bet.equals(bet1.bet) : bet1.bet != null) return false;
        if (money != null ? !money.equals(bet1.money) : bet1.money != null) return false;
        if (status != null ? !status.equals(bet1.status) : bet1.status != null) return false;
        return match != null ? match.equals(bet1.match) : bet1.match == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (match_id != null ? match_id.hashCode() : 0);
        result = 31 * result + (betResult != null ? betResult.hashCode() : 0);
        result = 31 * result + (bet != null ? bet.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (match != null ? match.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", match_id=" + match_id +
                ", betResult='" + betResult + '\'' +
                ", bet=" + bet +
                ", money=" + money +
                ", status='" + status + '\'' +
                '}';
    }
}
