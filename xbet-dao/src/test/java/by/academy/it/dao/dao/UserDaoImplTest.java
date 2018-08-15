package by.academy.it.dao.dao;

import by.academy.it.dao.UserDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    private UserDao userDao = DaoFactory.getInstance().getUserDao();

    @Test
    public void createUser() throws Exception {
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("123");
        user.setFirstName("Qwerty");
        user.setLastName("Qwerty");
        user.setEmail("qwerty@mail.ru");
        user.setBalance(100500d);
        user.setRole(2);
        userDao.create(user);
        User user1 = userDao.findByLogin(user.getLogin());
        assertEquals(user.getLogin(), user1.getLogin());
        userDao.delete(user1.getId());
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setLogin("user");
        user.setPassword("Password2");
        user.setFirstName("User");
        user.setLastName("User");
        user.setEmail("user@mail.ru");
        user.setBalance(150d);
        user.setRole(2);
        userDao.updateBalance(user.getLogin(), user.getBalance());
    }

    @Test
    public void findByLogin() throws Exception {
        User user = userDao.findByLogin("admin");
        assertNotNull(user);
    }

    @Test
    public void findById() throws Exception {
        User user = userDao.findById(1);
        assertNotNull(user);
    }

}