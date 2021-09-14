package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.UserDao;
import org.example.jdbc.task3.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class UserServiceTest {

    private final UserDao userDao = mock(UserDao.class);
    private final UUID userId = UUID.randomUUID();
    private final User user = new User();
    private final Date currentDate = new Date();

    private final UserService userService = new UserService(userDao);

    @Before
    public void init() {
        user.setId(userId);
        user.setName("test name");
        user.setSurname("test surname");
        user.setBirthDay(new Timestamp(currentDate.getTime()));
    }

    @Test
    public void saveUser() {
        when(userDao.saveUser(any(User.class))).thenReturn(user);

        User actualUser = this.userService.saveUser(user);

        assertEquals(actualUser, user);
        verify(userDao, times(1)).saveUser(user);
    }

    @Test
    public void getUserById() {
        when(userDao.getUserById(any(UUID.class))).thenReturn(user);

        User actualUser = this.userService.getUserById(userId);

        assertEquals(actualUser, user);
        verify(userDao, times(1)).getUserById(userId);
    }

    @Test
    public void getUsers() {
        when(userDao.getUsers()).thenReturn(Arrays.asList(user));

        assertEquals(userService.getUsers().get(0).getId(), user.getId());
        verify(userDao, times(1)).getUsers();
    }
}
