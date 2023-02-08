package com.dku.springstudy.like.controller;

import com.dku.springstudy.auth.JwtProvider;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.item.repository.ItemRepository;
import com.dku.springstudy.like.dto.CreateLikeDto;
import com.dku.springstudy.like.entity.Like;
import com.dku.springstudy.like.repository.LikeRepository;
import com.dku.springstudy.like.service.LikeService;
import com.dku.springstudy.member.entity.Member;
import com.dku.springstudy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;
    private final ItemRepository itemRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    @PostMapping
    public ResponseEntity<Like.LikeId> likeItem(@RequestBody CreateLikeDto dto,
                                                HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        Member member = memberRepository.findByEmail(jwtProvider.extractEmail(token)).orElseThrow();
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow();
        Like like = likeService.createLike(member, item);
        Like save = likeRepository.save(like);

        return ResponseEntity.ok().body(save.getLikeId());
    }

    @PostMapping("/unCheck")
    public ResponseEntity<String> unCheckLike(@RequestBody CreateLikeDto dto,
                                             HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        Member member = memberRepository.findByEmail(jwtProvider.extractEmail(token)).orElseThrow();
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow();
        Like.LikeId likeId = new Like.LikeId(member.getId(), item.getId());
        Like like = likeRepository.findById(likeId).orElseThrow();
        like.unCheckLike();

        return ResponseEntity.ok().body("성공!");
    }
}
