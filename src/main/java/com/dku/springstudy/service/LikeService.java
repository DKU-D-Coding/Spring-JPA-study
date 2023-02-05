package com.dku.springstudy.service;

import com.dku.springstudy.domain.Likes;
import com.dku.springstudy.domain.Product;
import com.dku.springstudy.domain.User;
import com.dku.springstudy.dto.like.LikeClickResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.LikeRepository;
import com.dku.springstudy.repository.ProductRepository;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public LikeClickResponseDto clickLikeBtn(Long memberId, Long productId) {
        User user = userRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_ID_NOT_FOUND));

        return checkActivity(user, product);
    }

    private LikeClickResponseDto checkActivity(User user, Product product) {
        Optional<Likes> likeByUserAndProduct = likeRepository.findByUserAndProduct(user, product);

        if (likeByUserAndProduct.isPresent()) { // 좋아요를 누른적이 있다면 취소
            Likes like = likeByUserAndProduct.get();
            likeRepository.delete(like);
            return LikeClickResponseDto.of("cancel");
        } else {
            Likes like = Likes.builder()
                                .user(user)
                                .product(product)
                                .build();
            likeRepository.save(like);
            return LikeClickResponseDto.of("add");
        }
    }
}
