package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Likes;
import com.dku.springstudy.domain.Product;
import com.dku.springstudy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndProduct(User user, Product product);
}
