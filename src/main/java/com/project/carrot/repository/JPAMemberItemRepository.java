package com.project.carrot.repository;

import com.project.carrot.domain.Member;
import com.project.carrot.domain.MemberItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JPAMemberItemRepository implements MemberItemRepository{

    private final EntityManager em;

    public JPAMemberItemRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    //cannot reliably process 'persist' call 오류 발생으로 어노테이션 추가
    public MemberItem save(MemberItem memberItem) {
        em.persist(memberItem);
        return memberItem;
    }

    @Override
    public List<MemberItem> findAll() {
        return em.createQuery("select m from MemberItem m",MemberItem.class)
                .getResultList();
    }

    @Override
    public List<MemberItem> findByCategory(String ItemCategory) {
        return em.createQuery("select m from MemberItem m where ItemCategory= :ItemCategory"
        ,MemberItem.class).setParameter("ItemCategory",ItemCategory).getResultList();
    }

    @Override
    public List<MemberItem> findByTitle(String ItemTitle) {
        return em.createQuery("select m from MemberItem m where ItemTitle= :ItemTitle"
                ,MemberItem.class).setParameter("ItemTitle",ItemTitle).getResultList();
    }

    @Override
    public List<MemberItem> findByContent(String ItemContent) {
        return em.createQuery("SELECT m FROM MemberItem m WHERE m.ItemContent LIKE %:ItemContent"
        ,MemberItem.class).setParameter("ItemContent",ItemContent).getResultList();
    }

    @Override
    public Optional<MemberItem> findByItemId(Long ItemId){
        MemberItem memberItem=em.find(MemberItem.class,ItemId);
        return Optional.ofNullable(memberItem);
    }

    @Override
    public Optional<Member> findByUserId(Long UserId){
        Member member=em.find(Member.class,UserId);
        return Optional.ofNullable(member);
    }

    @Override
    public List<MemberItem> findAllByUserId(Long UserId){
        return em.createQuery("select m from MemberItem m where UserId= :UserId"
                ,MemberItem.class).setParameter("UserId",UserId).getResultList();
    }
}
