package com.dku.springstudy.repository;

import com.dku.springstudy.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Long> {

}
