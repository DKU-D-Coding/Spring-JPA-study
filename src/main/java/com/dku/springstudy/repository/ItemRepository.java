package com.dku.springstudy.repository;

import com.dku.springstudy.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
