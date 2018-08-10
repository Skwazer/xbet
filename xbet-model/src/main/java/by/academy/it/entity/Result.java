package by.academy.it.entity;

import java.io.Serializable;

/**
 * Representation of database table 'results'.
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 8270753393039084570L;

    private Integer id;
    private Integer matchId;
    private String result;
    private Integer winnerId;
    private Integer loserId;
    private Integer winnerGoals;
    private Integer loserGoals;

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
     * The {@code matchId} field getter.
     * @return a value of the {@code matchId} field.
     */
    public Integer getMatchId() {
        return matchId;
    }

    /**
     * The {@code matchId} field setter.
     * @param matchId value to set.
     */
    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    /**
     * The {@code result} field getter.
     * @return a value of the {@code result} field.
     */
    public String getResult() {
        return result;
    }

    /**
     * The {@code result} field setter.
     * @param result value to set.
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * The {@code winnerId} field getter.
     * @return a value of the {@code winnerId} field.
     */
    public Integer getWinnerId() {
        return winnerId;
    }

    /**
     * The {@code winnerId} field setter.
     * @param winnerId value to set.
     */
    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    /**
     * The {@code loserId} field getter.
     * @return a value of the {@code loserId} field.
     */
    public Integer getLoserId() {
        return loserId;
    }

    /**
     * The {@code loserId} field setter.
     * @param loserId value to set.
     */
    public void setLoserId(Integer loserId) {
        this.loserId = loserId;
    }

    /**
     * The {@code winnerGoals} field getter.
     * @return a value of the {@code winnerGoals} field.
     */
    public Integer getWinnerGoals() {
        return winnerGoals;
    }

    /**
     * The {@code winnerGoals} field setter.
     * @param winnerGoals value to set.
     */
    public void setWinnerGoals(Integer winnerGoals) {
        this.winnerGoals = winnerGoals;
    }

    /**
     * The {@code loserGoals} field getter.
     * @return a value of the {@code loserGoals} field.
     */
    public Integer getLoserGoals() {
        return loserGoals;
    }

    /**
     * The {@code loserGoals} field setter.
     * @param loserGoals value to set.
     */
    public void setLoserGoals(Integer loserGoals) {
        this.loserGoals = loserGoals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result1 = (Result) o;

        if (id != result1.id) return false;
        if (matchId != result1.matchId) return false;
        if (winnerId != result1.winnerId) return false;
        if (loserId != result1.loserId) return false;
        if (winnerGoals != result1.winnerGoals) return false;
        if (loserGoals != result1.loserGoals) return false;
        return result != null ? result.equals(result1.result) : result1.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + matchId;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + winnerId;
        result1 = 31 * result1 + loserId;
        result1 = 31 * result1 + winnerGoals;
        result1 = 31 * result1 + loserGoals;
        return result1;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", matchId=" + matchId +
                ", result='" + result + '\'' +
                ", winnerId=" + winnerId +
                ", loserId=" + loserId +
                ", winnerGoals=" + winnerGoals +
                ", loserGoals=" + loserGoals +
                '}';
    }
}
