package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
