package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.LikeDao;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class LikeServiceTest {

    private final LikeDao likeDao = mock(LikeDao.class);
    private final UUID postId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final Date currentDate = new Date();

    private final LikeService likeService = new LikeService(likeDao);

    @Test
    public void addLike() {
        likeService.addLike(postId, userId, new Timestamp(currentDate.getTime()));
        verify(likeDao, times(1)).addLike(postId, userId, new Timestamp(currentDate.getTime()));
    }
}
