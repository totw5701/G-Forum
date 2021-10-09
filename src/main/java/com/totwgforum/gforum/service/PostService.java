package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.dto.PostDtoRes;
import com.totwgforum.gforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Long create(Post post){
        postRepository.save(post);
        return post.getId();
    }

    public Boolean delete(Long postId){
        Post findOne = postRepository.findOne(postId);
        postRepository.remove(findOne);
        if(findOne == null){
            return true;
        }
        return false;
    }

    public Post update(Long postId, Post newOne){
        // dirty checking
        Post oldOne = postRepository.findOne(postId);
        oldOne.setTitle(newOne.getTitle());
        oldOne.setDescription(newOne.getDescription());
        return oldOne;
    }

    public List<Post> findAll(){
         return postRepository.findAll();
    }

    public List<Post> listUp(){
        List<Post> rowPosts = postRepository.findAll();
        Collections.sort(rowPosts, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                if(o1.getCreated().isAfter(o2.getCreated())) return -1;
                return 1;
            }
        });
        return rowPosts;
    }

    public Post findById(Long postId){
        return postRepository.findOne(postId);
    }

}
