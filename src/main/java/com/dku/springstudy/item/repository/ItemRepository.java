package com.dku.springstudy.item.repository;

import com.dku.springstudy.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{
}
