package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.domain.UserRole;
import com.totwgforum.gforum.dto.post.PostDtoRes;
import com.totwgforum.gforum.dto.post.PostSaveFormReq;
import com.totwgforum.gforum.dto.post.PostUpdateFormReq;
import com.totwgforum.gforum.repository.PostRepository;
import com.totwgforum.gforum.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;

    @Autowired PostRepository postRepository;
    @Autowired UserRepository userRepository;
    @Autowired HttpSession httpSession;

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
    public void 글_등록(){
        // given
        User user = userRepository.findByEmail("email11test!@#@");

        PostSaveFormReq form = new PostSaveFormReq();
        form.setAuthor(user.getId());
        form.setDescription("테스트용 글 내용");
        form.setTitle("테스트용 글 제목");

        // when
        Long saveId = postService.create(form);

        //then
        Post one = postRepository.findOne(saveId);
        assertThat(one.getTitle()).isEqualTo("테스트용 글 제목");
    }

    @Test
    public void 글_조회(){
        // given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post = new Post();
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        post.setTitle("테스트용 글 제목");
        post.setDescription("내용!");

        postRepository.save(post);

        // when
        PostDtoRes byId = postService.findById(post.getId());

        //then
        assertThat(byId.getTitle()).isEqualTo("테스트용 글 제목");
    }

    @Test
    public void 글삭제(){
        // given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post = new Post();
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        post.setTitle("테스트용 글 제목");
        post.setDescription("내용!");

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        //then
        Post one = postRepository.findOne(post.getId());
        assertThat(one).isNull();
    }

    @Test
    public void 글수정(){
        // given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post = new Post();
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        post.setTitle("테스트용 글 제목");
        post.setDescription("내용!");

        postRepository.save(post);

        PostUpdateFormReq form = new PostUpdateFormReq();
        form.setAuthor(user.getId());
        form.setId(post.getId());
        form.setTitle("수정된 제목");
        form.setDescription("수정된 내용");

        // when
        Long updatePostId = postService.update(form);

        Post updatePost = postRepository.findOne(updatePostId);

        //then
        assertThat(updatePost.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatePost.getDescription()).isEqualTo("수정된 내용");
    }

    @Test
    void 글_검색() {
        //Given
        User user = userRepository.findByEmail("email11test!@#@");

        Post post1 = new Post();
        post1.setUser(user);
        post1.setCreated(LocalDateTime.now());
        post1.setTitle("테스트용 글 제목 qweqw!!e2323@");
        post1.setDescription("내용!");

        postRepository.save(post1);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setCreated(LocalDateTime.now());
        post2.setTitle("테스트용 글 제목2222 qweqw!!e2323@");
        post2.setDescription("내용!");

        postRepository.save(post2);

        long countSearch = (long) postRepository.findCountSearch("qweqw!!e2323@");
        int pageNum = (int)(countSearch/20 + 1);
        if(countSearch%20 == 0) pageNum--;


        //When
        List<PostDtoRes> posts = postService.findPageSearch(pageNum, pageNum, "qweqw!!e2323@");
        
        //Then
        assertThat(posts.size()).isEqualTo(2);
    }
}