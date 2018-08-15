package by.academy.it.entity;

import java.io.Serializable;

/**
 * Representation of database table 'results'.
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 8270753393039084570L;

    private Integer id;
    private Integer matchId;
    private Character result;
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
    public Character getResult() {
        return result;
    }

    /**
     * The {@code result} field setter.
     * @param result value to set.
     */
    public void setResult(Character result) {
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

        if (id != null ? !id.equals(result1.id) : result1.id != null) return false;
        if (matchId != null ? !matchId.equals(result1.matchId) : result1.matchId != null) return false;
        if (result != null ? !result.equals(result1.result) : result1.result != null) return false;
        if (winnerId != null ? !winnerId.equals(result1.winnerId) : result1.winnerId != null) return false;
        if (loserId != null ? !loserId.equals(result1.loserId) : result1.loserId != null) return false;
        if (winnerGoals != null ? !winnerGoals.equals(result1.winnerGoals) : result1.winnerGoals != null) return false;
        return loserGoals != null ? loserGoals.equals(result1.loserGoals) : result1.loserGoals == null;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (matchId != null ? matchId.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (winnerId != null ? winnerId.hashCode() : 0);
        result1 = 31 * result1 + (loserId != null ? loserId.hashCode() : 0);
        result1 = 31 * result1 + (winnerGoals != null ? winnerGoals.hashCode() : 0);
        result1 = 31 * result1 + (loserGoals != null ? loserGoals.hashCode() : 0);
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
