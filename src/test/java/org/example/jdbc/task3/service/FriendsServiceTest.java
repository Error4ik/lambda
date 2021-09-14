package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.FriendsDao;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class FriendsServiceTest {

    private final FriendsDao friendsDao = mock(FriendsDao.class);
    private final UUID firstUserId = UUID.randomUUID();
    private final UUID secondUserId = UUID.randomUUID();

    private final FriendsService friendsService = new FriendsService(friendsDao);

    @Test
    public void addFriend() {
        friendsService.addFriend(firstUserId, secondUserId);
        verify(friendsDao, times(1)).addFriend(firstUserId, secondUserId);
    }
}
