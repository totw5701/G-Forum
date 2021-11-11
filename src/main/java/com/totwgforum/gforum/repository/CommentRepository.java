package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }

    public Comment findOne(Long commentId){
        return em.find(Comment.class, commentId);
    }

    public List<Comment> findAll(){
        return em.createQuery("select c from Comment c order by c.id desc", Comment.class).getResultList();
    }

    public List<Comment> findAllInPost(Long postId) {
        String query = "select c from Comment c where c.postId = "+postId+" order by c.id";
        return em.createQuery(query, Comment.class).getResultList();
    }

    public void remove(Comment comment){
        em.remove(comment);
    }

}
