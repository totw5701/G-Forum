package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public Post findOne(Long postId){
        return em.find(Post.class, postId);
    }

    public void remove(Post post){
        em.remove(post);
    }


}
