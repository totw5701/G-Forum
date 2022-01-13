package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.domain.UserRole;
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

    @Autowired UserRepository userRepository;

    @BeforeEach
    void before() {
        postRepository.clear();

        User user = new User();
        user.setEmail("email11test!@#@");
        user.setNickName("nick23@#testNick");
        user.setRegisterDate(LocalDateTime.now());
        user.setRole(UserRole.ROLE_USER);
        userRepository.save(user);
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
        User user = userRepository.findByEmail("email11test!@#@");


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

        postRepository.save(post);
        postRepository.save(post2);

        // When
        List<Post> list = postRepository.findAll();

        // Then
        assertThat(list.size()).isGreaterThan(1);
    }

    @Test
    void 글_삭제(){
        // Given
        Post post = new Post();
        post.setUser(new User());
        post.setCreated(LocalDateTime.now());
        post.setDescription("description");
        post.setTitle("title");

        postRepository.save(post);

        // When
        Post before = postRepository.findOne(post.getId());
        assertThat(before.getTitle()).isEqualTo("title");

        postRepository.remove(post);

        // Then
        Post after = postRepository.findOne(post.getId());
        assertThat(after).isNull();

    }
}