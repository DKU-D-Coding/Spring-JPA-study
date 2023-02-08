package com.dku.springstudy.controller;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.AddItemDto;
import com.dku.springstudy.dto.ItemDetailsDto;
import com.dku.springstudy.dto.ItemDto;
import com.dku.springstudy.enums.Category;
import com.dku.springstudy.repository.jpa.MemberRepository;
import com.dku.springstudy.service.ImageFileService;
import com.dku.springstudy.service.ItemService;
import com.dku.springstudy.service.MemberService;
import com.dku.springstudy.service.S3Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final ImageFileService imageFileService;
    private final S3Upload s3Upload;

    @PostMapping("/add")
    private AddItemDto addItem(@RequestPart("data") AddItemDto itemDto, @RequestPart("images")MultipartFile[] multipartFiles) throws IOException {
        Member currentLoginMember = getCurrentLoginMember();

        Item item = Item.createItem(currentLoginMember, itemDto.getTitle(), itemDto.getContent(), itemDto.getPrice(), itemDto.getCategory());
        itemService.addItem(item);

        if(multipartFiles.length != 0){
            for(MultipartFile multipartFile : multipartFiles){
                String imagePath = s3Upload.upload(multipartFile);
                ImageFile imageFile = ImageFile.createImageFile(imagePath);
                imageFile.updateItem(item);
                imageFileService.save(imageFile);
            }
        }

        return itemDto;
    }

    @GetMapping("/details/{itemId}")
    public ItemDetailsDto details(@PathVariable("itemId") Long itemId){
        Item findItem = itemService.findById(itemId);
        Member seller = findItem.getMember();
        List<ItemDto> result = itemService.findByMember(seller);

        return new ItemDetailsDto(
                findItem.getMember().getNickname(),
                findItem.getCategory().getCategory(),
                findItem.getLastModifiedDate(),
                findItem.getImages().stream().map(ImageFile::getImageUrl).collect(Collectors.toList()),
                findItem.getTitle(),
                findItem.getContent(),
                findItem.getPrice(),
                result
                );
    }

    @GetMapping("/details/all/{sellerId}")
    public List<ItemDto> sellerItems(@PathVariable("sellerId") Long sellerId){
        Member seller = memberService.findById(sellerId);
        return itemService.findByMember(seller);
    }

    @GetMapping("/update/{itemId}")
    public ItemDto transferPreviousItemInfo(@PathVariable Long itemId){
        return itemService.transferPreviousItemInfo(itemId);
    }

    @PostMapping("/update/{itemId}")
    public void updateItem( @PathVariable("itemId") Long itemId,
                            @RequestPart("data") AddItemDto updateItemDto,
                            @RequestPart("images")MultipartFile[] updateMultipartFiles) throws IOException {

        itemService.updateItem(
                itemId,
                updateItemDto.getTitle(),
                updateItemDto.getContent(),
                updateItemDto.getCategory(),
                updateItemDto.getPrice(),
                updateMultipartFiles
        );
    }

    @PostMapping("/delete/{itemId}")
    public void deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
    }

    private Member getCurrentLoginMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        return memberService.getCurrentLoginMember(loginEmail);
    }


}
