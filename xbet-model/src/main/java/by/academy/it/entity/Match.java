package by.academy.it.entity;


import java.io.Serializable;
import java.sql.Date;

/**
 * Representation of database table 'matches'.
 */
public class Match implements Serializable{

    private static final long serialVersionUID = -582400334161547669L;

    private Integer id;
    private Date date;
    private Integer team1_id;
    private Integer team2_id;
    private Double victory1;
    private Double draw;
    private Double victory2;
    private Double victory1OrDraw;
    private Double victory1Or2;
    private Double victory2OrDraw;
    private Team team1;
    private Team team2;

    /**
     * The {@code team1} field getter.
     * @return a value of the {@code team1} field.
     */
    public Team getTeam1() {
        return team1;
    }

    /**
     * The {@code team1} field setter.
     * @param team1 value to set.
     */
    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    /**
     * The {@code team2} field getter.
     * @return a value of the {@code team2} field.
     */
    public Team getTeam2() {
        return team2;
    }

    /**
     * The {@code team2} field setter.
     * @param team2 value to set.
     */
    public void setTeam2(Team team2) {
        this.team2 = team2;
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
     * The {@code date} field getter.
     * @return a value of the {@code date} field.
     */
    public Date getDate() {
        return date;
    }

    /**
     * The {@code date} field setter.
     * @param date value to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * The {@code team1_id} field getter.
     * @return a value of the {@code team1_id} field.
     */
    public Integer getTeam1_id() {
        return team1_id;
    }

    /**
     * The {@code team1_id} field setter.
     * @param team1_id value to set.
     */
    public void setTeam1_id(Integer team1_id) {
        this.team1_id = team1_id;
    }

    /**
     * The {@code team2_id} field getter.
     * @return a value of the {@code team2_id} field.
     */
    public Integer getTeam2_id() {
        return team2_id;
    }

    /**
     * The {@code team2_id} field setter.
     * @param team2_id value to set.
     */
    public void setTeam2_id(Integer team2_id) {
        this.team2_id = team2_id;
    }

    /**
     * The {@code victory1} field getter.
     * @return a value of the {@code victory1} field.
     */
    public Double getVictory1() {
        return victory1;
    }

    /**
     * The {@code victory1} field setter.
     * @param victory1 value to set.
     */
    public void setVictory1(Double victory1) {
        this.victory1 = victory1;
    }

    /**
     * The {@code draw} field getter.
     * @return a value of the {@code draw} field.
     */
    public Double getDraw() {
        return draw;
    }

    /**
     * The {@code draw} field setter.
     * @param draw value to set.
     */
    public void setDraw(Double draw) {
        this.draw = draw;
    }

    /**
     * The {@code victory2} field getter.
     * @return a value of the {@code victory2} field.
     */
    public Double getVictory2() {
        return victory2;
    }

    /**
     * The {@code victory2} field setter.
     * @param victory2 value to set.
     */
    public void setVictory2(Double victory2) {
        this.victory2 = victory2;
    }

    /**
     * The {@code victory1OrDraw} field getter.
     * @return a value of the {@code victory1OrDraw} field.
     */
    public Double getVictory1OrDraw() {
        return victory1OrDraw;
    }

    /**
     * The {@code victory1OrDraw} field setter.
     * @param victory1OrDraw value to set.
     */
    public void setVictory1OrDraw(Double victory1OrDraw) {
        this.victory1OrDraw = victory1OrDraw;
    }

    /**
     * The {@code victory1Or2} field getter.
     * @return a value of the {@code victory1Or2} field.
     */
    public Double getVictory1Or2() {
        return victory1Or2;
    }

    /**
     * The {@code victory1Or2} field setter.
     * @param victory1Or2 value to set.
     */
    public void setVictory1Or2(Double victory1Or2) {
        this.victory1Or2 = victory1Or2;
    }

    /**
     * The {@code victory2OrDraw} field getter.
     * @return a value of the {@code victory2OrDraw} field.
     */
    public Double getVictory2OrDraw() {
        return victory2OrDraw;
    }

    /**
     * The {@code victory2OrDraw} field setter.
     * @param victory2OrDraw value to set.
     */
    public void setVictory2OrDraw(Double victory2OrDraw) {
        this.victory2OrDraw = victory2OrDraw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (id != null ? !id.equals(match.id) : match.id != null) return false;
        if (date != null ? !date.equals(match.date) : match.date != null) return false;
        if (team1_id != null ? !team1_id.equals(match.team1_id) : match.team1_id != null) return false;
        if (team2_id != null ? !team2_id.equals(match.team2_id) : match.team2_id != null) return false;
        if (victory1 != null ? !victory1.equals(match.victory1) : match.victory1 != null) return false;
        if (draw != null ? !draw.equals(match.draw) : match.draw != null) return false;
        if (victory2 != null ? !victory2.equals(match.victory2) : match.victory2 != null) return false;
        if (victory1OrDraw != null ? !victory1OrDraw.equals(match.victory1OrDraw) : match.victory1OrDraw != null)
            return false;
        if (victory1Or2 != null ? !victory1Or2.equals(match.victory1Or2) : match.victory1Or2 != null) return false;
        if (victory2OrDraw != null ? !victory2OrDraw.equals(match.victory2OrDraw) : match.victory2OrDraw != null)
            return false;
        if (team1 != null ? !team1.equals(match.team1) : match.team1 != null) return false;
        return team2 != null ? team2.equals(match.team2) : match.team2 == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (team1_id != null ? team1_id.hashCode() : 0);
        result = 31 * result + (team2_id != null ? team2_id.hashCode() : 0);
        result = 31 * result + (victory1 != null ? victory1.hashCode() : 0);
        result = 31 * result + (draw != null ? draw.hashCode() : 0);
        result = 31 * result + (victory2 != null ? victory2.hashCode() : 0);
        result = 31 * result + (victory1OrDraw != null ? victory1OrDraw.hashCode() : 0);
        result = 31 * result + (victory1Or2 != null ? victory1Or2.hashCode() : 0);
        result = 31 * result + (victory2OrDraw != null ? victory2OrDraw.hashCode() : 0);
        result = 31 * result + (team1 != null ? team1.hashCode() : 0);
        result = 31 * result + (team2 != null ? team2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", date=" + date +
                ", team1_id=" + team1_id +
                ", team2_id=" + team2_id +
                ", victory1=" + victory1 +
                ", draw=" + draw +
                ", victory2=" + victory2 +
                ", victory1OrDraw=" + victory1OrDraw +
                ", victory1Or2=" + victory1Or2 +
                ", victory2OrDraw=" + victory2OrDraw +
                '}';
    }

}
