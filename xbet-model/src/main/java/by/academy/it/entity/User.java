package by.academy.it.entity;

import java.io.Serializable;

/**
 * Representation of database table 'users'.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -7975258222781331767L;

    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Integer balance;
    private Integer role;

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
     * The {@code login} field getter.
     * @return a value of the {@code login} field.
     */
    public String getLogin() {
        return login;
    }

    /**
     * The {@code login} field setter.
     * @param login value to set.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * The {@code password} field getter.
     * @return a value of the {@code password} field.
     */
    public String getPassword() {
        return password;
    }

    /**
     * The {@code password} field setter.
     * @param password value to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The {@code firstName} field getter.
     * @return a value of the {@code firstName} field.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The {@code firstName} field setter.
     * @param firstName value to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The {@code lastName} field getter.
     * @return a value of the {@code lastName} field.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The {@code lastName} field setter.
     * @param lastName value to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The {@code email} field getter.
     * @return a value of the {@code email} field.
     */
    public String getEmail() {
        return email;
    }

    /**
     * The {@code email} field setter.
     * @param email value to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The {@code balance} field getter.
     * @return a value of the {@code balance} field.
     */
    public Integer getBalance() {
        return balance;
    }

    /**
     * The {@code balance} field setter.
     * @param balance value to set.
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    /**
     * The {@code role} field getter.
     * @return a value of the {@code role} field.
     */
    public Integer getRole() {
        return role;
    }

    /**
     * The {@code role} field setter.
     * @param role value to set.
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (balance != user.balance) return false;
        if (role != user.role) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + balance;
        result = 31 * result + role;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}
