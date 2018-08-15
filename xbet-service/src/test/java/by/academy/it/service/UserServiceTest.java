package by.academy.it.service;

import by.academy.it.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService = UserService.getInstance();


    @Test
    public void updateUser() throws Exception {
        User user = userService.updateUserBalance("user", 100);
        assertNotNull(user);
    }



    @Test
    public void isPasswordCorrectForLogin() throws Exception {
        assertTrue(userService.isPasswordCorrectForLogin("admin", "Password1"));
    }

    @Test
    public void findUser() throws Exception {
        assertEquals("admin", userService.findUserByLogin("admin").getLogin());
    }

}