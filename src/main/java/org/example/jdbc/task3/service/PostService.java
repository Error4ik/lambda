package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.PostDao;
import org.example.jdbc.task3.entity.Post;

import java.util.List;
import java.util.UUID;

public class PostService {

    private final PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public Post savePost(Post post) {
        return this.postDao.savePost(post);
    }

    public Post getPostById(UUID postId) {
        return this.postDao.getPostById(postId);
    }

    public List<Post> getPosts() {
        return this.postDao.getPosts();
    }

    public void savePostsWithBatch(List<Post> posts) {
        this.postDao.savePostsWithBatch(posts);
    }
}
