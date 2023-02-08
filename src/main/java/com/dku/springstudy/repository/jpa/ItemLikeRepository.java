package com.dku.springstudy.repository.jpa;


import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.ItemLike;
import com.dku.springstudy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    List<ItemLike> findByItem(Item item);

    List<ItemLike> findByMember(Member member);

    Optional<ItemLike> findByMemberAndItem(Member member, Item item);
}
