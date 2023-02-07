package com.dku.springstudy.item.controller;

import com.dku.springstudy.auth.JwtProvider;
import com.dku.springstudy.global.PageResponse;
import com.dku.springstudy.item.dto.CreateItemDto;
import com.dku.springstudy.item.dto.ItemResponseDto;
import com.dku.springstudy.item.service.ItemService;
import com.dku.springstudy.member.entity.Member;
import com.dku.springstudy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Long> createItem(HttpServletRequest request,
                                           @RequestBody CreateItemDto dto) {
        String token = jwtProvider.resolveToken(request);
        Member member = memberRepository.findByEmail(jwtProvider.extractEmail(token)).orElseThrow();
        Long item = itemService.createItem(member, dto);

        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getOneItem(@RequestBody Long itemId) {
        ItemResponseDto item = itemService.getItemById(itemId);

        return ResponseEntity.ok().body(item);
    }

    @GetMapping
    public PageResponse<ItemResponseDto> getAllItem(@RequestParam(value = "query") String query,
                                                    @RequestParam(value = "category") String category,
                                                    Pageable pageable) {
        Page<ItemResponseDto> items = itemService.getItemByParam(query, category, pageable);
        return new PageResponse<>(items.getContent(), items.getPageable(), items.getTotalElements());
    }
}
