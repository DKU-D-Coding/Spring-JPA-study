package com.dku.springstudy.domain.product.repository;

import com.dku.springstudy.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findProductByUserId(Long userId);
}
