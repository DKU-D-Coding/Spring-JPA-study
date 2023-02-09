package com.dku.springstudy.repository;

import com.dku.springstudy.model.Items;
import com.dku.springstudy.model.Likes;
import com.dku.springstudy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("select distinct i from Items i join i.images im")
    List<Items> findItemsWithImages();

    List<Items> findByUser(User user);

    @Override
    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select i from Items i where i.id = :id")
    Optional<Items> findById(final Long id);
}
