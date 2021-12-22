package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired PostRepository postRepository;


    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(false)
    public void 더미데이터넣기(){

        User user = userRepository.findAll().get(0);

        for(int i = 0; i < 300; i++) {
            Post post = new Post();
            post.setTitle("asdasd");
            post.setDescription("테스트용 더미 내용.");
            post.setUser(user);
            post.setCreated(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    @Test
    void 글_저장_받기(){

        // Given
        Post post = new Post();
        post.setUser(new User());
        post.setCreated(LocalDateTime.now());
        post.setDescription("description");
        post.setTitle("title");

        postRepository.save(post);

        // When
        Post one = postRepository.findOne(post.getId());

        // Then
        assertThat(one.getId()).isEqualTo(post.getId());
    }

    @Test
    void 모든_글_받기(){
        // Given
        User user = new User();
        user.setRegisterDate(LocalDateTime.now());
        user.setEmail("sdfasdfasdf@asdf.com");
        user.setPassword("1");
        user.setNickName("asdasd");
        user.setId(325L);


        Post post = new Post();
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        post.setDescription("description");
        post.setTitle("title");

        Post post2 = new Post();
        post2.setUser(user);
        post2.setCreated(LocalDateTime.now());
        post2.setDescription("description");
        post2.setTitle("title");


        System.out.println("save");
        postRepository.save(post);
        postRepository.save(post2);

        // When
        System.out.println("findAll");
        List<Post> list = postRepository.findAll();

        // Then
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void 글_삭제(){
        // Given

        // When

        // Then

    }

    @Test
    void 모든_글_갯수(){
        // Given

        // When

        // Then

    }

    @Test
    void 특정_페이지_글_리스트_받기(){
        // Given

        // When

        // Then

    }

    @Test
    void 글_검색_받기(){
        // Given

        // When

        // Then

    }


}