package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class PostRepository {
    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public void remove(Post post){
        Session session = em.unwrap(Session.class);
        session.remove(em.contains(post) ? post : em.merge(post));
        session.flush();
        em.close();
    }

    public Optional<List<Post>> findAll(){
        return Optional.of(em.createQuery("select p from Post p", Post.class).getResultList());
    }

    public Optional<Post> findById(Long postId){
        return em.createQuery("select p from Post p where  p.id = :postId", Post.class)
                .setParameter("postId",postId)
                .getResultList().stream().findAny();
    }

}
