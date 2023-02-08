package com.dku.springstudy.like.repository;

import com.dku.springstudy.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Like.LikeId> {

}
