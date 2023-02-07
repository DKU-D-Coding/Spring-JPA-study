package com.dku.springstudy.service;

import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.ItemLike;
import com.dku.springstudy.repository.jpa.ItemLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;

    public int getLikeCountByItemId(Item item){
        List<ItemLike> itemLikes = itemLikeRepository.findByItem(item);
        return itemLikes.size();
    }
}
