package org.example.jdbc.task3.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jdbc.task3.entity.Post;
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

public class PostDao {

    private final static String SAVE_POST = "INSERT INTO posts (user_id, text) VALUES(?, ?);";
    private final static String GET_POST_BY_ID = "SELECT * FROM posts as p WHERE p.id = ?;";
    private final static String GET_ALL_POSTS = "SELECT * FROM posts;";

    private static final Logger logger = LogManager.getLogger();
    private final ComboPooledDataSource dataSource;

    public PostDao(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Post savePost(Post post) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_POST, Statement.RETURN_GENERATED_KEYS)) {
            pr.setObject(1, post.getUserId(), Types.OTHER);
            pr.setString(2, post.getText());
            pr.executeUpdate();
            try (ResultSet resultSet = pr.getGeneratedKeys()) {
                while (resultSet.next()) {
                    post.setId(UUID.fromString(resultSet.getString(1)));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format("%s - was saved.", post));
        return post;
    }

    public Post getPostById(UUID postId) {
        Post post = new Post();
        try (Connection conn = dataSource.getConnection(); PreparedStatement pr = conn.prepareStatement(GET_POST_BY_ID)) {
            pr.setObject(1, postId, Types.OTHER);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    post.setId(UUID.fromString(rs.getString(1)));
                    post.setUserId(UUID.fromString(rs.getString(2)));
                    post.setText(rs.getString(3));
                    post.setDateOfCreation(
                            new Timestamp(DateParser.parseDateFromString(rs.getString(4)).getTime()));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format("%s - was returned.", post));
        return post;
    }

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pr = conn.prepareStatement(GET_ALL_POSTS);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                Post post = new Post();
                post.setId(UUID.fromString(rs.getString(1)));
                post.setUserId(UUID.fromString(rs.getString(2)));
                post.setText(rs.getString(3));
                post.setDateOfCreation(new Timestamp(DateParser.parseDateFromString(rs.getString(4)).getTime()));
                posts.add(post);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return posts;
    }

    public void savePostsWithBatch(List<Post> posts) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pr = con.prepareStatement(SAVE_POST)) {
            con.setAutoCommit(false);
            for (Post post : posts) {
                pr.setObject(1, post.getUserId(), Types.OTHER);
                pr.setString(2, post.getText());
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
