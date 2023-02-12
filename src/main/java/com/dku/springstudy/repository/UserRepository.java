package com.dku.springstudy.repository;

import com.dku.springstudy.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class UserRepository {
    private final EntityManager entityManager;

    public void save(User user){
        entityManager.persist(user);
    }

    public void remove(User user){
        Session session = entityManager.unwrap(Session.class);
        session.remove(entityManager.contains(user) ? user : entityManager.merge(user));
        session.flush();
        entityManager.close();
    }

    public Optional<User> findById(Long id){
        User user = entityManager.find(User.class, id);
        
        return Optional.ofNullable(user);
    }

    public Optional<User> findByName(String name) {
        return entityManager.
                createQuery("select m from user m where m.name = :name",
                        User.class)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    public Optional<User> findByEmail(String email){
        return entityManager.
                createQuery("select m from user m where m.email = :email", User.class)
                .setParameter("email", email)
                .getResultList().stream().findAny();
    }
}
