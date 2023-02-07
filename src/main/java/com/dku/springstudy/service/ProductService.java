package com.dku.springstudy.service;

import com.dku.springstudy.domain.Product;
import com.dku.springstudy.domain.User;
import com.dku.springstudy.domain.constant.Category;
import com.dku.springstudy.dto.product.request.CreateRequestDto;
import com.dku.springstudy.dto.product.response.CreateResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.ProductRepository;
import com.dku.springstudy.repository.UserRepository;
import com.dku.springstudy.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    /**
     * 글 등록에 필요한 데이터를 적절히 입력받아 Product 테이블에 저장, 사진들을 S3에 업로드하고 File 테이블에 저장한다.
     *
     * @param dto 글 등록에 필요한 정보
     * @param file 업로드할 사진들
     * @param loginMemberId 로그인한 사용자의 아이디(PK)
     * @return CreateResponseDto
     */
    @Transactional
    public CreateResponseDto createPost(CreateRequestDto dto, List<MultipartFile> file, Long loginMemberId) {
        User user = userRepository.findById(loginMemberId)
                                        .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));

        Product product = Product.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .category(Category.valueOf(dto.getCategory()))
                .build();

        productRepository.save(product);

        List<String> fileUrls = s3Service.uploadFiles(file, product); // S3에 이미지 업로드

        return CreateResponseDto.of(fileUrls, product.getTitle(), product.getCategory(), product.getPrice(), product.getContent());
    }
}
