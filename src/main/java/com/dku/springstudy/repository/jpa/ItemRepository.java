package com.dku.springstudy.repository.jpa;

import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByMember(Member member);
}
