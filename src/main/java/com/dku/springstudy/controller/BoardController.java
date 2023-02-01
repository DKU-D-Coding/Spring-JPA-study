package com.dku.springstudy.controller;

import com.dku.springstudy.dto.BoardDTO;
import com.dku.springstudy.dto.ResponseDTO;
import com.dku.springstudy.service.BoardService;
import com.dku.springstudy.service.S3Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final S3Service s3Service;
    private final BoardService boardService;

    @ApiOperation(value = "게시판 글쓰기", notes = "여러장의 이미지와 제목,가격,카테고리,게시글 내용 값을 받아서 게시판을 작성한다")
    @PostMapping("/board")
    public ResponseDTO<?> uploadFile(@AuthenticationPrincipal String userId,
                                     BoardDTO boardDTO, @RequestPart("file") List<MultipartFile> file) {
        List<String> imgPaths = boardService.multipleUpload(file);
        boardDTO.setImageUrls(imgPaths);
        boardService.write(boardDTO, userId);
        return new ResponseDTO<>(HttpStatus.OK.value(), boardDTO);
    }
}
