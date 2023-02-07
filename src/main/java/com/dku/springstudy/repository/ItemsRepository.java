package com.dku.springstudy.repository;

import com.dku.springstudy.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("select distinct i from Items i join i.images im")
    List<Items> findItemsWithImages();
}
