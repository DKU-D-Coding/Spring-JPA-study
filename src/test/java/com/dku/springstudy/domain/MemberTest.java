package com.dku.springstudy.domain;

import com.dku.springstudy.enums.Category;
import com.dku.springstudy.repository.ItemLikeRepository;
import com.dku.springstudy.repository.ItemRepository;
import com.dku.springstudy.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EntityTest {
    @PersistenceContext
    EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemLikeRepository itemLikeRepository;

    @Test
    public void createMember(){
        //given
        Member member = new Member();
        member.createMember("test@naver.com", "1234", "동현", "010-9358-4027", "홍홍홍");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByUsername("동현").get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void createItem(){
        //given
        Member member = new Member();
        member.createMember("test@naver.com", "1234", "동현", "010-9358-4027", "홍홍홍");
        memberRepository.save(member);

        //when
        Item item = new Item();
        item.createItem(member, "title", "content", 10000, Category.ELECTRONIC);
        itemRepository.save(item);

        //then
        assertThat(item.getMember()).isEqualTo(member);

    }

    @Test
    public void createLike(){
        //given
        Member member1 = new Member();
        member1.createMember("test@naver.com", "1234", "동현", "010-9358-4027", "홍홍홍");
        memberRepository.save(member1);
        Member member2 = new Member();
        member2.createMember("qwe123@gmail.com", "qwe123", "dong", "010-1234-5678", "공공공");
        memberRepository.save(member2);

        Item item = new Item();
        item.createItem(member1, "title", "content", 10000, Category.ELECTRONIC);
        itemRepository.save(item);

        //when
        ItemLike itemLike1 = new ItemLike();
        itemLike1.createItemLike(item, member1);
        ItemLike itemLike2 = new ItemLike();
        itemLike2.createItemLike(item, member2);
        itemLikeRepository.save(itemLike1);
        itemLikeRepository.save(itemLike2);

        //then
        assertThat(itemLike1.getMember().getUsername()).isEqualTo("동현");
        assertThat(itemLike2.getMember().getUsername()).isEqualTo("dong");
        assertThat(itemLike1.getItem().getTitle()).isEqualTo("title");
        assertThat(itemLike2.getItem().getContent()).isEqualTo("content");
    }

}