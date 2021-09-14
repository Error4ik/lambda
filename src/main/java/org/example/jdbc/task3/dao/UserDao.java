package org.example.jdbc.task3.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jdbc.task3.entity.User;
import org.example.jdbc.task3.util.DateParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDao {

    private final static String SAVE_USER = "INSERT INTO users (name, surname) VALUES(?, ?);";
    private final static String GET_USER_BY_ID = "SELECT * FROM users as u WHERE u.id = ?;";
    private final static String GET_ALL_USERS = "SELECT * FROM users;";
    private final static String GET_ALL_NAMES_WHERE_100_FRIENDS_AND_100_LIKES = "SELECT u.name FROM users as u " +
            "JOIN friendships as f ON (u.id = f.user_id1 or u.id = f.user_id2) where u.id IN (SELECT p.user_id " +
            "FROM posts as p JOIN likes as l ON (p.id = l.post_id) WHERE l.date >= '2025-03-01' " +
            "AND l.date < date '2025-03-01' + integer '31' GROUP BY p.user_id HAVING count(*) > ?)" +
            " GROUP BY u.name HAVING count(*) > ?;";

    private static final Logger logger = LogManager.getLogger();
    private final ComboPooledDataSource dataSource;

    public UserDao(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User saveUser(User user) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, user.getName());
            pr.setString(2, user.getSurname());
            pr.executeUpdate();
            try (ResultSet resultSet = pr.getGeneratedKeys()) {
                while (resultSet.next()) {
                    user.setId(UUID.fromString(resultSet.getString(1)));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format("%s - was saved.", user));
        return user;
    }

    public User getUserById(UUID userId) {
        User user = new User();
        try (Connection conn = dataSource.getConnection(); PreparedStatement pr = conn.prepareStatement(GET_USER_BY_ID)) {
            pr.setObject(1, userId, Types.OTHER);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    user.setId(UUID.fromString(rs.getString(1)));
                    user.setName(rs.getString(2));
                    user.setSurname(rs.getString(3));
                    user.setBirthDay(new Timestamp(DateParser.parseDateFromString(rs.getString(4)).getTime()));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format("%s - was returned.", user));
        return user;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(GET_ALL_USERS);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString(1)));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setBirthDay(new Timestamp(DateParser.parseDateFromString(rs.getString(4)).getTime()));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return users;
    }

    public List<String> getAllNamesWhereFriendsAndLikes(int countOfFriends, int countOfLikes) {
        List<String> strings = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(GET_ALL_NAMES_WHERE_100_FRIENDS_AND_100_LIKES)) {
            pr.setInt(1, countOfFriends);
            pr.setInt(2, countOfLikes);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    strings.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return strings;
    }

    public void saveUsersWithBatch(List<User> users) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_USER)) {
            con.setAutoCommit(false);
            for (User u : users) {
                pr.setString(1, u.getName());
                pr.setString(2, u.getSurname());
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
