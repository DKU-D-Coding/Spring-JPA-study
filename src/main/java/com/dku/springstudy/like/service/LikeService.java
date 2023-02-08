package com.dku.springstudy.like.service;

import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.like.entity.Like;
import com.dku.springstudy.like.repository.LikeRepository;
import com.dku.springstudy.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public Like createLike(Member member, Item item) {
        Like.LikeId likeId = new Like.LikeId(member.getId(), item.getId());
        Like like;
        Optional<Like> byId = likeRepository.findById(likeId);
        if (byId.isPresent()) {
            like = byId.get();
            like.checkLike();
        }else {
            like = new Like(member, item);
            member.addLike(like);
            item.addLike(like);
            likeRepository.save(like);
        }
        return like;
    }

    public void unCheckLike(Like.LikeId likeId) {
        Like like = likeRepository.findById(likeId).orElseThrow();
        like.unCheckLike();
    }
}
