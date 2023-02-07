package com.dku.springstudy.repository.jpa;


import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.ItemLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    List<ItemLike> findByItem(Item item);
}
