package com.dku.springstudy.controller;

import com.dku.springstudy.dto.common.SuccessResponse;
import com.dku.springstudy.dto.product.request.CreateRequestDto;
import com.dku.springstudy.dto.product.response.CreateResponseDto;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;

    @Operation(
            summary = "상품 등록",
            description = "로그인된 사용자가 상품의 사진 / 제목 / 카테고리 / 가격 / 내용을 입력하면 상품 글을 등록한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "아이디(PK)가 존재하지 않는 경우"),
            @ApiResponse(responseCode = "500", description = "파일의 업로드가 실패했거나 파일 확장자가 올바르지 않는 경우")
    })
    @PostMapping("/product")
    public ResponseEntity<SuccessResponse<CreateResponseDto>> createPost(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Parameter(description = "<code>data</code> 키 값으로 CreateRequestDto의 필드들을 입력한다.")
            @RequestPart("data") CreateRequestDto dto,
            @Parameter(description = "<code>file</code> 키 값으로 이미지들을 입력한다.")
            @RequestPart(value = "file", required = false) List<MultipartFile> file
    ) {

        CreateResponseDto response = productService.createPost(dto, file, customUserDetails.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(response));
    }
}
