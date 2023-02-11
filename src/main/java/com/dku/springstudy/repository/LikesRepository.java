package com.dku.springstudy.repository;

import com.dku.springstudy.model.Items;
import com.dku.springstudy.model.Likes;
import com.dku.springstudy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByItemsAndUser(Items items, User user);

    List<Likes> findByUser(User user);

}
