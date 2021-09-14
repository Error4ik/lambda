package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.UserDao;
import org.example.jdbc.task3.entity.User;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User saveUser(User user) {
        return this.userDao.saveUser(user);
    }

    public User getUserById(UUID userId) {
        return this.userDao.getUserById(userId);
    }

    public List<User> getUsers() {
        return this.userDao.getUsers();
    }

    public List<String> getAllNamesWhereFriendsAndLikes(int countOfFriends, int countOfLikes) {
        return this.userDao.getAllNamesWhereFriendsAndLikes(countOfFriends, countOfLikes);
    }

    public void saveUsersWithBatch(List<User> list) {
        this.userDao.saveUsersWithBatch(list);
    }
}
