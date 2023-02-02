package com.dku.springstudy.repository;

import com.dku.springstudy.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images, Long> {
}
