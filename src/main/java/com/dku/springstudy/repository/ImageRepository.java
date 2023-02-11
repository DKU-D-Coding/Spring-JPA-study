package com.dku.springstudy.repository;

import com.dku.springstudy.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Images, Long> {

    @Transactional
    @Modifying
    @Query("delete from Images im where im.items.id = :id")
    void deleteByItemsId(Long id);
}
