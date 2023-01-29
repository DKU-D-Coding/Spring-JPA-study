package com.dku.springstudy.repository.jpa;

import com.dku.springstudy.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
