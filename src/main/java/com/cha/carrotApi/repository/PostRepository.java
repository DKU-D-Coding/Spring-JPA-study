package com.cha.carrotApi.repository;

import com.cha.carrotApi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
