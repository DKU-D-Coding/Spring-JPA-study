package com.dku.springstudy.controller;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.domain.Item;
import com.dku.springstudy.dto.ItemDto;
import com.dku.springstudy.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/home")
    public List<ItemDto> home(){
        List<Item> allItems = itemService.findAll();
        return allItems.stream()
                .map(i -> new ItemDto(
                        i.getId(),
                        i.getImages().stream().map(ImageFile::getImageUrl).collect(Collectors.toList()),
                        i.getTitle(),
                        i.getContent(),
                        i.getPrice(),
                        i.getLikes().size())
                )
                .collect(Collectors.toList());
    }

}
