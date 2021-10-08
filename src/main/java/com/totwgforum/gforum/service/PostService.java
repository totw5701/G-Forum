package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Long create(Post post){
        postRepository.save(post);
        return post.getId();
    }

    public Post delete(Long postId){
        Post findOne = postRepository.findOne(postId);
        postRepository.remove(findOne);
        return findOne;
    }

    public Post update(Long postId, Post newOne){
        Post oldOne = postRepository.findOne(postId);
        oldOne.setTitle(newOne.getTitle());
        oldOne.setDescription(newOne.getDescription());
        return oldOne;
    }

    public Post findById(Long postId){
        return postRepository.findOne(postId);
    }

}
