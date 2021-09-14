package org.example.jdbc.task3.service;

import org.example.jdbc.task3.dao.PostDao;
import org.example.jdbc.task3.entity.Post;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


public class PostServiceTest {

    private final PostDao postDao = mock(PostDao.class);
    private final UUID postId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final Date currentDate = new Date();
    private final Post post = new Post();

    private final PostService postService = new PostService(postDao);

    @Before
    public void init() {
        post.setId(postId);
        post.setUserId(userId);
        post.setText("some text");
        post.setDateOfCreation(new Timestamp(currentDate.getTime()));
    }

    @Test
    public void saveUser() {
        when(postDao.savePost(any(Post.class))).thenReturn(post);

        Post actualPost = this.postService.savePost(post);

        assertEquals(actualPost, post);
        verify(postDao, times(1)).savePost(post);
    }

    @Test
    public void getPostById() {
        when(postDao.getPostById(any(UUID.class))).thenReturn(post);

        Post actualPost = this.postService.getPostById(postId);

        assertEquals(actualPost, post);
        verify(postDao, times(1)).getPostById(postId);
    }

    @Test
    public void getPosts() {
        when(postDao.getPosts()).thenReturn(Arrays.asList(post));

        assertEquals(postService.getPosts().get(0).getId(), post.getId());
        verify(postDao, times(1)).getPosts();
    }
}
