package com.dku.springstudy.controller;

import com.dku.springstudy.dto.ItemsDTO;
import com.dku.springstudy.dto.ResponseDTO;
import com.dku.springstudy.model.Category;
import com.dku.springstudy.model.Images;
import com.dku.springstudy.model.Items;
import com.dku.springstudy.repository.ItemsRepository;
import com.dku.springstudy.service.ImageService;
import com.dku.springstudy.service.ItemsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemsService itemsService;
    private final ItemsRepository itemsRepository;
    private final ImageService imageService;

    @ApiOperation(value = "게시판 글쓰기", notes = "여러장의 이미지와 제목,가격,카테고리,게시글 내용 값을 받아서 게시판을 작성한뒤 게시판 id 반환")
    @PostMapping("/board")
    public ResponseDTO<?> uploadFile(@AuthenticationPrincipal String userId, ItemsDTO itemsDTO,
                                     @RequestPart("file") List<MultipartFile> file) {
        Long itemId = imageService.multipleUpload(file, userId, itemsDTO);
        return new ResponseDTO<>(HttpStatus.OK.value(), itemId);
    }

    /**
     * 화면에 맞는 dto, api스펙, 무한 스크롤 페이징 고민해야 한다
     * @return
     */
    @ApiOperation(value = "게시판 보기", notes = "사진,제목,가격,장소, 등을 게시판을 통해 보여준다")
    @GetMapping("/board")
    public ResponseDTO<?> index(){
        Items itemLists = itemsService.index();
        //List<ItemsResponseDTO> result = itemLists.stream()
        //        .map(b -> new ItemsResponseDTO(b))
        //        .collect(Collectors.toList());
        return new ResponseDTO<>(HttpStatus.OK.value(), itemLists);
    }

    @ApiOperation(value = "상품 카데고리 보기", notes = "다양한 상품의 카테고리 정보를 제공한다")
    @GetMapping("/enum")
    public ResponseDTO<?> category(){
        return new ResponseDTO<>(HttpStatus.OK.value(), Category.values());
    }

    @ApiOperation(value = "상품 삭제하기", notes = "게시글에 올린 상품을 삭제할 수 있다")
    @DeleteMapping("/board/{itemId}")
    public ResponseDTO<?> delete(@AuthenticationPrincipal String userId, @PathVariable Long itemId){
        Items items = itemsRepository.findById(itemId).orElseThrow(()->new IllegalStateException("게시글에 올린 상품이 없음"));
        List<Images> images = items.getImages();
        imageService.deleteFile(images);
        itemsRepository.deleteById(itemId);
        return new ResponseDTO<>(HttpStatus.OK.value(), "삭제 완료");
    }

    @ApiOperation(value = "상품정보 수정하기", notes = "상품정보를 수정하며 이미지가 있는 경우 기존의 이미지 파일을 삭제합니다.")
    @PatchMapping("/board/{itemId}")
    public ResponseDTO<?> update(@AuthenticationPrincipal String userId, ItemsDTO itemsDTO,
                                 @RequestPart("file") List<MultipartFile> file, @PathVariable Long itemId) {

        imageService.multipleModify(file, userId, itemsDTO, itemId);
        return new ResponseDTO<>(HttpStatus.OK.value(), "수정완료");
    }
}