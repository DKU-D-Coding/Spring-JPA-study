package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public void remove(Member member){
        Session session = em.unwrap(Session.class);
        session.remove(em.contains(member) ? member : em.merge(member));
        session.flush();
        em.close();
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Optional<Member> findByName(String name) {
        return em.
                createQuery("select m from Member m where m.name = :name",
                        Member.class)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    public Optional<Member> findByEmail(String email){
        return em.
                createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList().stream().findAny();
    }


}
