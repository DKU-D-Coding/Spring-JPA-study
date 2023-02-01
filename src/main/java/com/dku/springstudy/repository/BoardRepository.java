package com.dku.springstudy.repository;

import com.dku.springstudy.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
