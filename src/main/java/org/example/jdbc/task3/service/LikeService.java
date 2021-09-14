package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.LikeDao;
import org.example.jdbc.task3.entity.Like;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class LikeService {

    private final LikeDao likeDao;

    public LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public void addLike(UUID postId, UUID userId, Timestamp date) {
        this.likeDao.addLike(postId, userId, date);
    }

    public void saveLikesWithBatch(List<Like> likeList) {
        this.likeDao.saveLikesWithBatch(likeList);
    }
}
