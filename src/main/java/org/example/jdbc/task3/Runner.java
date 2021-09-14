package org.example.jdbc.task3;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jdbc.task3.dao.FriendsDao;
import org.example.jdbc.task3.dao.LikeDao;
import org.example.jdbc.task3.dao.PostDao;
import org.example.jdbc.task3.dao.UserDao;
import org.example.jdbc.task3.entity.Like;
import org.example.jdbc.task3.entity.Post;
import org.example.jdbc.task3.entity.User;
import org.example.jdbc.task3.service.FriendsService;
import org.example.jdbc.task3.service.LikeService;
import org.example.jdbc.task3.service.PostService;
import org.example.jdbc.task3.service.UserService;
import org.example.jdbc.task3.util.ComboPooledDS;
import org.example.jdbc.task3.util.DateParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Runner {
    private static final Logger logger = LogManager.getLogger();
    private static final ComboPooledDataSource dataSource = ComboPooledDS.getDatasource();

    private final static String CREATE_EXTENSION = "CREATE EXTENSION pgcrypto;";
    private final static String CREATE_USERS = "CREATE TABLE users(id UUID DEFAULT gen_random_uuid() PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, surname VARCHAR(255) UNIQUE NOT NULL, birth_date TIMESTAMP(0) DEFAULT now());";
    private final static String CREATE_POSTS = "CREATE TABLE posts(id UUID DEFAULT gen_random_uuid() PRIMARY KEY, " +
            "user_id UUID NOT NULL, text TEXT NOT NULL, date_of_creation TIMESTAMP(0) DEFAULT now(), " +
            "FOREIGN KEY(user_id) REFERENCES users (id));";
    private final static String CREATE_FRIENDSHIPS = "CREATE TABLE friendships(user_id1 UUID NOT NULL, user_id2 UUID NOT NULL," +
            "date TIMESTAMP(0) DEFAULT now(), PRIMARY KEY(user_id1, user_id2), FOREIGN KEY(user_id1) REFERENCES users(id), " +
            "FOREIGN KEY(user_id2) REFERENCES users(id));";
    private final static String CREATE_LIKES = "CREATE TABLE likes(post_id UUID NOT NULL, user_id UUID NOT NULL, " +
            "date TIMESTAMP(0) DEFAULT now(), PRIMARY KEY(post_id, user_id), FOREIGN KEY(post_id) REFERENCES posts(id), " +
            "FOREIGN KEY(user_id) REFERENCES users(id));";

    public static void main(String[] args) {
        logger.info("Creating Tables.");
        createTables();
        logger.info("Tables were created.");
        logger.info("-------------------------");

        UserService userService = new UserService(new UserDao(ComboPooledDS.getDatasource()));
        PostService postService = new PostService(new PostDao(ComboPooledDS.getDatasource()));
        FriendsService friendsService = new FriendsService(new FriendsDao(ComboPooledDS.getDatasource()));
        LikeService likeService = new LikeService(new LikeDao(ComboPooledDS.getDatasource()));

        logger.info("Filling users start");
        List<User> users = fillingUsersTable();
        userService.saveUsersWithBatch(users);
        logger.info("Filling users end");
        logger.info("-------------------------");

        logger.info("Filling posts start");
        List<Post> posts = fillingPostTable(userService.getUsers());
        postService.savePostsWithBatch(posts);
        logger.info("Filling posts end");
        logger.info("-------------------------");

        logger.info("Filling friends start");
        Map<User, List<User>> userListMap = fillingFriendshipsTable(userService.getUsers());
        friendsService.saveFriendsWithBatch(userListMap);
        logger.info("Filling friends end");
        logger.info("-------------------------");

        logger.info("Filling likes start");
        List<Like> likeList = fillingLikesTable(userService.getUsers(), postService.getPosts());
        likeService.saveLikesWithBatch(likeList);
        logger.info("Filling likes end");
        logger.info("-------------------------");

        List<String> allNames = userService.getAllNamesWhereFriendsAndLikes(100, 100);
        allNames.forEach(System.out::println);
        System.out.println(allNames.size());
        System.out.println("-------------END----------------");
    }

    private static void createTables() {
        try (Connection db = dataSource.getConnection()) {
            try (Statement dataQuery = db.createStatement()) {
//                dataQuery.execute(CREATE_EXTENSION);
                dataQuery.execute(CREATE_USERS);
                dataQuery.execute(CREATE_POSTS);
                dataQuery.execute(CREATE_FRIENDSHIPS);
                dataQuery.execute(CREATE_LIKES);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private static List<Like> fillingLikesTable(List<User> users, List<Post> posts) {
        List<Like> likeList = new ArrayList<>(500_000);
        Date startLikeDate = DateParser.parseDateFromString("2025-02-01");
        Date endLikeDate = DateParser.parseDateFromString("2025-04-31");
        int usersSize = users.size();
        int postsSize = posts.size();
        final int minLikesCount = 30;
        final int maxLikesCount = 350;
        Set<User> userSet = new HashSet<>();
        Set<Post> postSet = new HashSet<>();

        for (int j = 0; j < 5000; j++) {
            int postPosition = (int) (Math.random() * postsSize);
            Post post = posts.get(postPosition);
            if (postSet.add(post)) {
                int countOfLikes = (int) (Math.random() * maxLikesCount) + minLikesCount;
                for (int i = 0; i < countOfLikes; i++) {
                    int userPosition = (int) (Math.random() * usersSize);
                    Date randomDate = new Date(ThreadLocalRandom.current()
                            .nextLong(startLikeDate.getTime(), endLikeDate.getTime()));
                    User user = users.get(userPosition);
                    if (post.getUserId() != user.getId()) {
                        if (userSet.add(user)) {
                            likeList.add(new Like(post.getId(), user.getId(), new Timestamp(randomDate.getTime())));
                        }
                    }
                }
                userSet.clear();
            }
        }
        return likeList;
    }

    private static Map<User, List<User>> fillingFriendshipsTable(List<User> users) {
        Map<User, List<User>> userFriends = new HashMap<>();
        final int minFriendsCount = 20;
        final int maxFriendsCount = 100;
        int usersSize = users.size();

        for (User user : users) {
            List<User> userList = new ArrayList<>();
            int countOfFriends = (int) ((Math.random() * maxFriendsCount) + minFriendsCount);
            for (int j = 0; j < countOfFriends; j++) {
                int randomUserPosition = (int) (Math.random() * usersSize);
                User friend = users.get(randomUserPosition);
                if (user.getId() != friend.getId() && !userList.contains(friend)) {
                    userList.add(friend);
                }
            }
            userFriends.put(user, userList);
        }
        return userFriends;
    }

    private static List<Post> fillingPostTable(List<User> users) {
        List<Post> posts = new ArrayList<>(5_000);
        final int minPostsCount = 3;
        final int maxPostsCount = 30;

        for (User user : users) {
            int rnd = (int) (Math.random() * maxPostsCount) + minPostsCount;
            for (int i = 0; i < rnd; i++) {
                posts.add(new Post(user.getId(), "Some text " + i));
            }
        }
        return posts;
    }

    private static List<User> fillingUsersTable() {
        List<String> names = Arrays.asList(
                "Aleksey", "Aleksandr", "Irina", "Mikhail", "Anna", "Alena", "Maxim", "Igor", "Petya",
                "Vasya", "Marina", "Kiril", "Dmitriy", "Elena", "Fedor", "Katya", "Evgeniy", "Sergey"
        );

        List<String> surnames = Arrays.asList(
                "Petrov", "Semenova", "Gavrilova", "Ivanov", "Petrov", "Sidorov", "Kalinin", "Kovtun", "Sizov",
                "Smirnov", "Svetlov", "Kirilov", "Zakharova", "Malinina", "Serova", "Klimov", "Ukhova", "Moskvin"
        );

        int namesSize = names.size();
        int surnamesSize = surnames.size();
        List<User> users = new ArrayList<>(1000);

        for (int i = 0; i < 1500; i++) {
            users.add(new User(names.get((int) (Math.random() * namesSize)) + i,
                    surnames.get((int) (Math.random() * surnamesSize)) + i));
        }
        return users;
    }
}
