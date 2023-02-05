package com.dku.springstudy.controller;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.AddItemDto;
import com.dku.springstudy.enums.Category;
import com.dku.springstudy.repository.jpa.MemberRepository;
import com.dku.springstudy.service.ItemService;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;

    @PostMapping("/add")
    private AddItemDto addItem(@RequestBody AddItemDto itemDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        Member currentLoginMember = memberService.getCurrentLoginMember(loginEmail);


        Item item = Item.createItem(currentLoginMember, itemDto.getTitle(), itemDto.getContent(), itemDto.getPrice(), Category.valueOf(itemDto.getCategory()));

        if(itemDto.getImages()!= null){
            for (ImageFile image:itemDto.getImages()) {
                item.addImage(image);
            }
        }

        itemService.addItem(item);
        return itemDto;
    }


}
