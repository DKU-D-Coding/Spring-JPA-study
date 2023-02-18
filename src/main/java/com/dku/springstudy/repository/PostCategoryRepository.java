package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.domain.Post;
import com.dku.springstudy.domain.PostCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class PostCategoryRepository {
    private final EntityManager em;

    public void save(PostCategory postCategory){
        em.persist(postCategory);
    }

    public void remove(PostCategory postCategory){
        Session session = em.unwrap(Session.class);
        session.remove(em.contains(postCategory) ? postCategory : em.merge(postCategory));
        session.flush();
        em.close();
    }

    public Optional<Post> findPostByCategory(String category){
        return em.createQuery("select pc.post from PostCategory pc where pc.category = :category",Post.class)
                .setParameter("category", category).getResultList().stream().findAny();
    }

    public Optional<Category> findCategoryByPostId(Long postId){
        return em.createQuery("select pc.category from PostCategory pc where pc.post.post_id = :postId", Category.class)
                .setParameter("postId", postId).getResultList().stream().findAny();
    }


}
