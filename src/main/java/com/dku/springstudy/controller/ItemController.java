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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation(value = "상품 추가 API", notes = "상품 title, 상품 content, 상품 category, 상품 price, 상품 이미지들을 form-data형식으로 받아서 상품 추가")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @PostMapping("/add")
    private AddItemDto addItem(@RequestPart("data") AddItemDto itemDto, @RequestPart("images")MultipartFile[] multipartFiles) throws IOException {
        Member currentLoginMember = getCurrentLoginMember();
        log.info("currentLoginMember={}", currentLoginMember);
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


    @ApiOperation(value = "상품 상세 페이지 API", notes = "상품 판매자 닉네임, 상품 카테고리, 상품 등록일(최근 수정날짜), 상품 이미지 링크들, 상품 제목, 상품 내용, 상품 가격을 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )

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


    @ApiOperation(value = "해당 판매자의 모든 판매중인 상품 조회", notes = "판매자의 모든 판매상품들 조회. PathVariable로 판매자 id 필요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )

    @GetMapping("/details/all/{sellerId}")
    public List<ItemDto> sellerItems(@PathVariable("sellerId") Long sellerId){
        Member seller = memberService.findById(sellerId);
        return itemService.findByMember(seller);
    }


    @ApiOperation(value = "상품 수정 페이지 조회", notes = "상품 수정 페이지 이동. PathVariable로 상품 id 필요. 기존의 상품 정보가 입력되어있어야 하기에 기존의 상품 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @GetMapping("/update/{itemId}")
    public ItemDto transferPreviousItemInfo(@PathVariable Long itemId){
        return itemService.transferPreviousItemInfo(itemId);
    }


    @ApiOperation(value = "상품 수정 API", notes = "실제 상품 수정 API. PathVariable로 상품 id, form-data로 수정한 상품 정보들, 상품 이미지들 받음")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
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


    @ApiOperation(value = "상품 삭제 API", notes = "상품 삭제 API. PathVariable로 상품 id 필요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
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
