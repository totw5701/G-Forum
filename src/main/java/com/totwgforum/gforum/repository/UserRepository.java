package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public User findByEmail(String email) {
        List<User> result = em.createQuery("SELECT u FROM User u WHERE u.email ='" + email + "'", User.class).getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public User findByNickName(String nickName) {
        List<User> result = em.createQuery("SELECT u FROM User u WHERE u.nickName ='" + nickName + "'", User.class).getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u order by u.id desc", User.class).getResultList();
    }

    public void delete(Long id){
        em.remove(id);
    }

    // 테스트용
    public void deleteAll() {
        em.createQuery("DELETE FROM User u").executeUpdate();
    }
}
