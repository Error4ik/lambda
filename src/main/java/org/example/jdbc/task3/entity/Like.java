package org.example.jdbc.task3.entity;

import java.sql.Timestamp;
import java.util.UUID;

public class Like {
    private UUID postId;
    private UUID userId;
    private Timestamp date;

    public Like(UUID postId, UUID userId, Timestamp date) {
        this.postId = postId;
        this.userId = userId;
        this.date = date;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
