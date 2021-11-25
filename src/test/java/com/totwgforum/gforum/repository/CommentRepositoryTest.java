package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import org.assertj.core.api.Assertions;
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
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;

    @Test
    void 댓글_등록_조회() {
        //given
        Comment cm = new Comment();
        cm.setCreated(LocalDateTime.now());
        cm.setDescription("test");
        cm.setAuthor(new User());
        cm.setPost(new Post());

        // when
        commentRepository.save(cm);
        Comment one = commentRepository.findOne(cm.getId());

        // then
        assertThat(cm).isEqualTo(one);
    }

//    @Test
//    void 댓글등록() {
//
//        //given
//        Comment c1 = new Comment();
//        c1.setPostId(1L);
//        c1.setDescription("1111");
//        c1.setAuthor(3L);
//        c1.setCreated(LocalDateTime.now());
//
//        // when
//        commentRepository.save(c1);
//        Comment one = commentRepository.findOne(c1.getId());
//
//        // then
//        assertThat(c1).isEqualTo(one);
//
//    }

//    @Test
//    void postId받아오(){
//
//
//        //given
//        Comment c1 = new Comment();
//        c1.setPostId(1L);
//        c1.setDescription("1111");
//        c1.setAuthor(3L);
//        c1.setCreated(LocalDateTime.now());
//
//        Comment c2 = new Comment();
//        c2.setPostId(1L);
//        c2.setDescription("2222");
//        c2.setAuthor(3L);
//        c2.setCreated(LocalDateTime.now());
//
//
//        Comment c3 = new Comment();
//        c3.setPostId(2L);
//        c3.setDescription("3333");
//        c3.setAuthor(3L);
//        c3.setCreated(LocalDateTime.now());
//
//
//        commentRepository.save(c1);
//        commentRepository.save(c2);
//        commentRepository.save(c3);
//
//        //when
//        List<Comment> allInPost = commentRepository.findAllInPost(1L);
//
//        assertThat(allInPost.size()).isEqualTo(2);
//
//
//    }

}