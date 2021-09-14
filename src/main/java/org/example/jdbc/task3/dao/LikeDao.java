package org.example.jdbc.task3.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jdbc.task3.entity.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class LikeDao {

    private static final Logger logger = LogManager.getLogger();

    private final ComboPooledDataSource dataSource;
    private final static String SAVE_LIKE = "INSERT INTO likes (post_id, user_id, date) VALUES(?, ?, ?);";

    public LikeDao(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLike(UUID postId, UUID userId, Timestamp date) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_LIKE)) {
            pr.setObject(1, postId, Types.OTHER);
            pr.setObject(2, userId, Types.OTHER);
            pr.setTimestamp(3, date);
            pr.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format("PostId %s was liked by UserId %s", postId, userId));
    }

    public void saveLikesWithBatch(List<Like> likeList) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_LIKE)) {

            con.setAutoCommit(false);
            for (Like like : likeList) {
                pr.setObject(1, like.getPostId(), Types.OTHER);
                pr.setObject(2, like.getUserId(), Types.OTHER);
                pr.setTimestamp(3, like.getDate());
                pr.addBatch();
            }
            pr.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
