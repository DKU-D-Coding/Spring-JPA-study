package com.dku.springstudy.s3.repository;

import com.dku.springstudy.s3.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
