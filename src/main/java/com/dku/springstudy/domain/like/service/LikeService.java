package com.dku.springstudy.domain.like.service;

import com.dku.springstudy.domain.like.Likes;
import com.dku.springstudy.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public Likes addLike(Likes likes){
        return likeRepository.save(likes);
    }

    @Transactional
    public void deleteLike(Long productId, Long userId){
        likeRepository.deleteByProductIdAndUserId(productId, userId);
    }

}
