package com.dku.springstudy.controller;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.domain.Item;
import com.dku.springstudy.dto.ItemDto;
import com.dku.springstudy.service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "홈", notes = "등록된 전체 상품 리스트 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
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
                        i.getLikes().size(),
                        i.getStatus())
                )
                .collect(Collectors.toList());
    }

}
