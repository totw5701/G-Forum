package com.totwgforum.gforum;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.repository.CommentRepository;
import com.totwgforum.gforum.repository.PostRepository;
import com.totwgforum.gforum.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class dummyData {

    @Autowired UserRepository userRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;

    @Test
    void 테스트데이터(){
        User user = new User();
        user.setPassword("1111");
        user.setRegisterDate(LocalDateTime.now());
        user.setEmail("email@email.com");
        user.setNickName("testMan1");

        User user2 = new User();
        user.setPassword("1111");
        user.setRegisterDate(LocalDateTime.now());
        user.setEmail("email2@email.com");
        user.setNickName("testMan2");

        userRepository.save(user);
        userRepository.save(user2);

        Post post = new Post();
        post.setTitle("test");
        post.setDescription("test test");
        post.setAuthor(1L);
        post.setCreated(LocalDateTime.now());

        postRepository.save(post);



    }
}
