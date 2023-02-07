package com.dku.springstudy.item.service;

import com.dku.springstudy.item.dto.ItemResponseDto;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.item.entity.Status;
import com.dku.springstudy.item.repository.ItemRepository;
import com.dku.springstudy.item.dto.CreateItemDto;
import com.dku.springstudy.member.entity.Member;
import com.dku.springstudy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public Long createItem(Member member, CreateItemDto dto) {
        Item item = Item.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .content(dto.getContent())
                .seller(member)
                .status(Status.ON_SALE)
//                .image() 이미지 저장 시 추가
                .build();

        Item save = itemRepository.save(item);
        return save.getId();
    }

    public ItemResponseDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return new ItemResponseDto(item);
    }

    public Page<ItemResponseDto> getItemByParam(String query, String category, Pageable pageable) {
        Page<Item> itemByParam = itemRepository.findItemByParam(query, category, pageable);
        return itemByParam
                .map(ItemResponseDto::new);
    }
}
