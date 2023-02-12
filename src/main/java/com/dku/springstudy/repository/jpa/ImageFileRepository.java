package com.dku.springstudy.repository.jpa;

import com.dku.springstudy.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
}
