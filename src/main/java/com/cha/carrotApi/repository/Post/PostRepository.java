package com.cha.carrotApi.repository.Post;

import com.cha.carrotApi.domain.Post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitle(String keyword, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByCategoryId(Pageable pageable, int categoryId);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
