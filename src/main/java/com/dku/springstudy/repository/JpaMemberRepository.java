package com.dku.springstudy.repository;

import com.dku.springstudy.model.Member;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member newMember) {
        em.persist(newMember);
        return newMember;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        Member member = em.find(Member.class, email);
        return Optional.ofNullable(member);
    }
}
