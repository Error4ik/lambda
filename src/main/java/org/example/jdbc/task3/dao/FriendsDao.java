package org.example.jdbc.task3.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jdbc.task3.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FriendsDao {

    private static final Logger logger = LogManager.getLogger();

    private final ComboPooledDataSource dataSource;
    private final static String SAVE_FRIENDS = "INSERT INTO friendships (user_id1, user_id2) VALUES(?, ?);";

    public FriendsDao(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addFriend(UUID userId1, UUID userId2) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_FRIENDS)) {
            pr.setObject(1, userId1, Types.OTHER);
            pr.setObject(2, userId2, Types.OTHER);
            pr.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format("%s and %s - now friends.", userId1, userId2));
    }

    public void saveFriendsWithBatch(Map<User, List<User>> friends) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_FRIENDS)) {
            con.setAutoCommit(false);

            for (Map.Entry<User, List<User>> userListEntry : friends.entrySet()) {
                User user = userListEntry.getKey();
                for (User friend : userListEntry.getValue()) {
                    pr.setObject(1, user.getId(), Types.OTHER);
                    pr.setObject(2, friend.getId(), Types.OTHER);
                    pr.addBatch();
                }
            }
            pr.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
