package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.post.PostDtoRes;
import com.totwgforum.gforum.dto.post.PostSaveFormReq;
import com.totwgforum.gforum.dto.post.PostUpdateFormReq;
import com.totwgforum.gforum.repository.CommentRepository;
import com.totwgforum.gforum.repository.PostRepository;
import com.totwgforum.gforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Long create(PostSaveFormReq form){

        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        post.setTitle(form.getTitle());
        post.setDescription(form.getDescription());
        User user = userRepository.findOne(form.getAuthor());
        post.setUser(user);

        postRepository.save(post);
        return post.getId();
    }

    public Boolean delete(Long postId){
        Post findOne = postRepository.findOne(postId);

        if (findOne == null) {
            return false;
        }

        postRepository.remove(findOne);

        return true;
    }

    public Long update(Long postId, PostUpdateFormReq form){
        // dirty checking
        Post entity = postRepository.findOne(postId);
        entity.setTitle(form.getTitle());
        entity.setDescription(form.getDescription());
        return entity.getId();
    }

    public long findAllCount(){
        Object count = postRepository.findCount();
        return (long)count;
    }

    public long findAllCountSearch(String keyword){
        Object count = postRepository.findCountSearch(keyword);
        return (long)count;
    }

    public List<PostDtoRes> findPage(int pageNum, int nowPage){
        int reverseNowPage = pageNum - nowPage;
        List<Post> entities = postRepository.findList(reverseNowPage);

        List<PostDtoRes> posts = new ArrayList<>();
        for (Post rowPost : entities) {
            PostDtoRes post = new PostDtoRes();
            post.setTitle(rowPost.getTitle());
            post.setId(rowPost.getId());
            String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("MM-dd"));
            post.setDate(date);
            post.setAuthorNickname(rowPost.getUser().getNickName());

            posts.add(post);
        }


        return posts;
    }

    public List<PostDtoRes> findPageSearch(int pageNum, int nowPage, String keyword){
        int reverseNowPage = pageNum - nowPage;
        List<Post> entities = postRepository.findListSearch(reverseNowPage, keyword);

        List<PostDtoRes> posts = new ArrayList<>();
        for (Post rowPost : entities) {
            PostDtoRes post = new PostDtoRes();
            post.setTitle(rowPost.getTitle());
            post.setId(rowPost.getId());
            String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("MM-dd"));
            post.setDate(date);
            post.setAuthorNickname(rowPost.getUser().getNickName());

            posts.add(post);
        }

        return posts;
    }

    public PostDtoRes findById(Long postId){
        Post entity = postRepository.findOne(postId);

        PostDtoRes post = new PostDtoRes();
        post.setId(entity.getId());
        post.setAuthor(entity.getUser().getId());
        post.setAuthorNickname(entity.getUser().getNickName());
        post.setTitle(entity.getTitle());
        post.setDescription(entity.getDescription());

        String date = entity.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
        post.setDate(date);

        return post;
    }

}
