package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.FriendsDao;
import org.example.jdbc.task3.entity.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FriendsService {

    private final FriendsDao friendsDao;

    public FriendsService(FriendsDao friendsDao) {
        this.friendsDao = friendsDao;
    }

    public void addFriend(UUID userId1, UUID userId2) {
        this.friendsDao.addFriend(userId1, userId2);
    }

    public void saveFriendsWithBatch(Map<User, List<User>> friends) {
        this.friendsDao.saveFriendsWithBatch(friends);
    }
}
