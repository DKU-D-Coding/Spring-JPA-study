package com.cha.carrotApi.repository.Post;

import com.cha.carrotApi.domain.Post.LikePost;
import com.cha.carrotApi.domain.Post.Post;
import com.cha.carrotApi.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByPostAndUser(Post post, User user);
}
