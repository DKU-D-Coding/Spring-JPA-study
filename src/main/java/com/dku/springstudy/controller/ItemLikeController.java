package com.dku.springstudy.controller;

import com.dku.springstudy.service.ItemLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-like")
public class ItemLikeController {

    private final ItemLikeService itemLikeService;

    @PostMapping("/add/{itemId}")
    public void addItemLike(@PathVariable Long itemId){
        itemLikeService.addItemLike(itemId);
    }

    @PostMapping("/delete/{itemId}")
    public void deleteItemLike(@PathVariable Long itemId){
        itemLikeService.deleteItemLike(itemId);
    }
}
