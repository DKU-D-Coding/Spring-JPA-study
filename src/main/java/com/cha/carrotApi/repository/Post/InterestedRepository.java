package com.cha.carrotApi.repository.Post;

import com.cha.carrotApi.domain.Post.InterestedPost;
import com.cha.carrotApi.domain.Post.Post;
import com.cha.carrotApi.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestedRepository extends JpaRepository<InterestedPost, Long> {
    Optional<InterestedPost> findInterestedByPost(Post post);
    Optional<InterestedPost> findByPostAndUser(Post post, User user);
    List<InterestedPost> findAllByUser(User user);
}
