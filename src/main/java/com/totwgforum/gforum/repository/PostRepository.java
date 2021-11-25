package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
//        em.flush();
//        Long id = post.getId();
    }

    public Post findOne(Long postId){
        return em.find(Post.class, postId);
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p order by p.id desc", Post.class).getResultList();
    }

    public void remove(Post post){
        em.remove(post);
    }

    public Object findCount(){
        return em.createQuery("select count(p) from Post p").getSingleResult();
    }

    public Object findCountSearch(String keyword){
        String query = "select count(p) from Post p where p.title LIKE '%" + keyword + "%'";
        return em.createQuery(query).getSingleResult();
    }

    public List<Post> findList(int pageNum){
        return em.createQuery("select p from Post p order by p.id desc", Post.class)
                .setFirstResult(pageNum*20)
                .setMaxResults(20)
                .getResultList();
    }

    public List<Post> findListSearch(int pageNum, String keyword){
        String query = "select p from Post p where p.title LIKE '%" + keyword + "%' order by p.id desc ";
        return em.createQuery(query, Post.class)
                .setFirstResult(pageNum*20)
                .setMaxResults(20)
                .getResultList();
    }


    // 개발용

    public void clear(){
        em.clear();
    }
    public void flush() {
        em.flush();
    }
}
