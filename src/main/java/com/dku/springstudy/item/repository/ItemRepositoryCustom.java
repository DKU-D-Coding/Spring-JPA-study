package com.dku.springstudy.item.repository;

import com.dku.springstudy.item.entity.Category;
import com.dku.springstudy.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> findItemByParam(String query, String category, Pageable pageable);
}
