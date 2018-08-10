package by.academy.it.entity;

import java.io.Serializable;

/**
 * Representation of database table 'teams'.
 */
public class Team implements Serializable {

    private static final long serialVersionUID = -2352324225580131176L;

    private Integer id;
    private String name;


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
     * The {@code name} field getter.
     * @return a value of the {@code name} field.
     */
    public String getName() {
        return name;
    }

    /**
     * The {@code name} field setter.
     * @param name value to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        return name != null ? name.equals(team.name) : team.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
