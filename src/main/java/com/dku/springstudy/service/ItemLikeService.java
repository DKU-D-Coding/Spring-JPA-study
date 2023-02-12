package com.dku.springstudy.service;

import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.ItemLike;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.exception.KarrotException;
import com.dku.springstudy.repository.jpa.ItemLikeRepository;
import com.dku.springstudy.repository.jpa.ItemRepository;
import com.dku.springstudy.security.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;
    private final ItemRepository itemRepository;

    public int getLikeCountByItemId(Item item){
        List<ItemLike> itemLikes = itemLikeRepository.findByItem(item);
        return itemLikes.size();
    }

    @Transactional
    public void addItemLike(Long itemId) {
        Member currentMember = AuthenticationProvider.getCurrentMember();
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "해당 상품이 존재하지 않습니다."));

        ItemLike itemLike = ItemLike.createItemLike(findItem, currentMember);
        itemLikeRepository.save(itemLike);
    }

    @Transactional
    public void deleteItemLike(Long itemId) {
        Member currentMember = AuthenticationProvider.getCurrentMember();
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "해당 상품이 존재하지 않습니다."));

        ItemLike deleteItemLike = itemLikeRepository.findByMemberAndItem(currentMember, findItem)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "해당 좋아요 정보가 없습니다."));

        itemLikeRepository.delete(deleteItemLike);

    }
}
