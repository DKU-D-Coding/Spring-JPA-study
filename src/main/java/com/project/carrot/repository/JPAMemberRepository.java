package com.project.carrot.repository;

import com.project.carrot.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JPAMemberRepository implements MemberRepository{
    private final EntityManager em;

    public JPAMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long Userid){
        Member member=em.find(Member.class,Userid);
        return Optional.ofNullable(member);
    }
    @Override
    public Optional<Member> findByEmail(String UserEmail) {
        List<Member> result = em.createQuery("select m from Member m where UserEmail= :UserEmail"
        , Member.class).setParameter("UserEmail",UserEmail).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String UserName) {
        List<Member> result = em.createQuery("select m from Member m where UserName= :UserName"
                , Member.class).setParameter("UserName",UserName).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByPass(String UserPass) {
        List<Member> result = em.createQuery("select m from Member m where UserPass= :UserPass"
                , Member.class).setParameter("UserPass",UserPass).getResultList();

        return result.stream().findAny();
    }


}
