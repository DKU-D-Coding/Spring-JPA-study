package com.dku.springstudy.repository;


import com.dku.springstudy.domain.ItemLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
}
