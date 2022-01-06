package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;

//    @Test
//    public void 글등록(){
//        // given
//        Post post = new Post();
//        post.setTitle("첫글");
//        post.setDescription("테스트용 첫 글 내용.");
//        post.setAuthor(1L);
//        post.setCreated(LocalDateTime.now());
//
//        // when
//        Long saveId = postService.create(post);
//
//        //then
//        Post findOne = postService.findById(saveId);
//        assertThat(findOne.getId()).isEqualTo(saveId);
//    }
//
//    @Test
//    @Rollback(false)
//    public void 더미데이터넣기(){
//        for(int i = 0; i < 300; i++) {
//            Post post = new Post();
//            post.setTitle("asdasd");
//            post.setDescription("테스트용 더미 내용.");
//            post.setAuthor(1L);
//            post.setCreated(LocalDateTime.now());
//            postService.create(post);
//        }
//    }
//
//    @Test
//    public void 글삭제(){
//        // given
//        Post post = new Post();
//        post.setTitle("첫글");
//        post.setDescription("테스트용 첫 글 내용.");
//        post.setAuthor(1L);
//        post.setCreated(LocalDateTime.now());
//        Long saveId = postService.create(post);
//
//        // when
//        postService.delete(saveId);
//
//        //then
//        Post deleteOne = postService.findById(post.getId());
//        assertThat(deleteOne).isNull();
//    }

//    @Test
//    public void 글수정(){
//        // given
//        Post post = new Post();
//        post.setTitle("첫글");
//        post.setDescription("테스트용 첫 글 내용.");
//        post.setAuthor(1L);
//        post.setCreated(LocalDateTime.now());
//        Long saveId = postService.create(post);
//
//        Post newPost = new Post();
//        newPost.setTitle("수정글");
//        newPost.setDescription("수정글.");
//
//        // when
//        Long updatePostId = postService.update(post.getId(), newPost);
//
//        Post updatePost = postService.findById(updatePostId);
//
//        //then
//        assertThat(updatePost.getTitle()).isEqualTo("수정글");
//        assertThat(updatePost.getDescription()).isEqualTo("수정글.");
//    }

    @Test
    public void 글수받아오기(){
        postService.findAllCount();
    }
}