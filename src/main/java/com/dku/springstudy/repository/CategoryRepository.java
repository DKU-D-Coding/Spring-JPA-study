package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.service.PostService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {
    private final EntityManager em;
    @Transactional
    public void save(Category category){
        if(Optional.ofNullable(findByCategory(category.getCategory())).isPresent()){
            em.merge(category);
        }else {
            em.persist(category);
        }
    }
    @Transactional
    public void remove(Category category){
        Session session = em.unwrap(Session.class);
        session.remove(em.contains(category) ? category : em.merge(category));
        session.flush();
        em.close();
    }

    public Optional<List<Category>> findAll(){
        return Optional.ofNullable(em.createQuery("select c from Category c", Category.class).getResultList());
    }

    public Optional<Category> findByCategory(String category){
        return em.createQuery("select c from Category c where c.category = :category", Category.class)
                .setParameter("category", category)
                .getResultList().stream().findAny();
    }

}
