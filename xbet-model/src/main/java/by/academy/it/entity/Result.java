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
    private Integer team1_goals;
    private Integer team2_goals;
    private Match match;

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
     * The {@code team1_goals} field getter.
     * @return a value of the {@code team1_goals} field.
     */
    public Integer getTeam1_goals() {
        return team1_goals;
    }

    /**
     * The {@code team1_goals} field setter.
     * @param team1_goals value to set.
     */
    public void setTeam1_goals(Integer team1_goals) {
        this.team1_goals = team1_goals;
    }

    /**
     * The {@code team2_goals} field getter.
     * @return a value of the {@code team2_goals} field.
     */
    public Integer getTeam2_goals() {
        return team2_goals;
    }

    /**
     * The {@code team2_goals} field setter.
     * @param team2_goals value to set.
     */
    public void setTeam2_goals(Integer team2_goals) {
        this.team2_goals = team2_goals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result1 = (Result) o;

        if (id != null ? !id.equals(result1.id) : result1.id != null) return false;
        if (matchId != null ? !matchId.equals(result1.matchId) : result1.matchId != null) return false;
        if (result != null ? !result.equals(result1.result) : result1.result != null) return false;
        if (team1_goals != null ? !team1_goals.equals(result1.team1_goals) : result1.team1_goals != null) return false;
        if (team2_goals != null ? !team2_goals.equals(result1.team2_goals) : result1.team2_goals != null) return false;
        return match != null ? match.equals(result1.match) : result1.match == null;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (matchId != null ? matchId.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (team1_goals != null ? team1_goals.hashCode() : 0);
        result1 = 31 * result1 + (team2_goals != null ? team2_goals.hashCode() : 0);
        result1 = 31 * result1 + (match != null ? match.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", matchId=" + matchId +
                ", result='" + result + '\'' +
                ", team1_goals=" + team1_goals +
                ", team2_goals=" + team2_goals +
                '}';
    }
}
