package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.domain.UserRole;
import com.totwgforum.gforum.dto.comment.CommentDtoRes;
import com.totwgforum.gforum.dto.comment.CommentSaveFormReq;
import com.totwgforum.gforum.repository.CommentRepository;
import com.totwgforum.gforum.repository.PostRepository;
import com.totwgforum.gforum.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired CommentService commentService;

    @Autowired UserRepository userRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    public void before() {
        User user = new User();
        user.setEmail("email11test!@#@");
        user.setNickName("nick23@#testNick");
        user.setRegisterDate(LocalDateTime.now());
        user.setRole(UserRole.ROLE_USER);
        userRepository.save(user);
    }

    @Test
    void 댓글_생성_조회() {

        //Given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post = new Post();

        post.setTitle("test title");
        post.setDescription("test description");
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);


        CommentSaveFormReq form = new CommentSaveFormReq();

        form.setAuthor(user.getId());
        form.setDescription("test comment");
        form.setPostId(post.getId());
        form.setCreated(LocalDateTime.now());

        //When
        Long aLong = commentService.create(form);
        CommentDtoRes comment = commentService.findById(aLong);

        //Then
        assertThat(comment.getDescription()).isEqualTo("test comment");

    }

    @Test
    void 댓글_삭제() {
        //Given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post = new Post();

        post.setTitle("test title");
        post.setDescription("test description");
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setDescription("test description");
        comment.setCreated(LocalDateTime.now());

        commentRepository.save(comment);

        //When
        commentService.delete(comment.getId());

        //Then
        Comment one = commentRepository.findOne(comment.getId());
        assertThat(one).isNull();
    }

    @Test
    void 게시글_댓글_모두_조회() {

        //Given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post = new Post();

        post.setTitle("test title");
        post.setDescription("test description");
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setDescription("test description");
        comment.setCreated(LocalDateTime.now());

        Comment comment2 = new Comment();
        comment2.setPost(post);
        comment2.setAuthor(user);
        comment2.setDescription("test description2");
        comment2.setCreated(LocalDateTime.now());

        commentRepository.save(comment);
        commentRepository.save(comment2);

        //When
        List<CommentDtoRes> allInPost = commentService.findAllInPost(post.getId());

        //Then
        assertThat(allInPost.size()).isEqualTo(2);

    }

}