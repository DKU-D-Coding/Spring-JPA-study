package com.dku.springstudy.service;

import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.jpa.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
