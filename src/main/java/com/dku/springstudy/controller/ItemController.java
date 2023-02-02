package com.dku.springstudy.controller;

import com.dku.springstudy.dto.ItemsDTO;
import com.dku.springstudy.dto.ItemsResponseDTO;
import com.dku.springstudy.dto.ResponseDTO;
import com.dku.springstudy.model.Items;
import com.dku.springstudy.service.ImageService;
import com.dku.springstudy.service.ItemsService;
import com.dku.springstudy.service.S3Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final S3Service s3Service;
    private final ItemsService itemsService;
    private final ImageService imageService;

    @ApiOperation(value = "게시판 글쓰기", notes = "여러장의 이미지와 제목,가격,카테고리,게시글 내용 값을 받아서 게시판을 작성한다")
    @PostMapping("/board")
    public ResponseDTO<?> uploadFile(@AuthenticationPrincipal String userId,
                                     ItemsDTO itemsDTO, @RequestPart("file") List<MultipartFile> file) {

        itemsService.itemUpload(itemsDTO, userId);
        imageService.multipleUpload(file,userId, itemsDTO);
        return new ResponseDTO<>(HttpStatus.OK.value(), itemsDTO);
    }
    @ApiOperation(value = "게시판 보기", notes = "사진,제목,가격,장소, 등을 게시판을 통해 보여준다")
    @GetMapping("/board")
    public ResponseDTO<?> index(){
        List<Items> itemLists = itemsService.index();
        List<ItemsResponseDTO> result = itemLists.stream()
                .map(b -> new ItemsResponseDTO(b))
                .collect(Collectors.toList());
        return new ResponseDTO<>(HttpStatus.OK.value(), result);
    }
}