package com.dku.springstudy.service;

import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.exception.KarrotException;
import com.dku.springstudy.repository.jpa.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long addItem(Item item){
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "해당 상품이 존재하지 않습니다."));
    }

    public List<Item> findByMember(Member member) {
        return itemRepository.findByMember(member);
    }
}
