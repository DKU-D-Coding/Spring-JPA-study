package com.dku.springstudy.domain.like.repository;

import com.dku.springstudy.domain.like.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    void deleteByProductIdAndUserId(Long productId, Long userId);
    // void deleteByProductId(productId);
}
