package by.academy.it.entity;

import java.io.Serializable;

/**
 * Representation of database table 'roles'.
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 9024477094482009883L;

    private Integer id;
    private String role;

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
     * The {@code role} field getter.
     * @return a value of the {@code role} field.
     */
    public String getRole() {
        return role;
    }

    /**
     * The {@code role} field setter.
     * @param role value to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        if (id != role1.id) return false;
        return role != null ? role.equals(role1.role) : role1.role == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
