package com.dku.springstudy.domain.like.controller;

import com.dku.springstudy.domain.like.Likes;
import com.dku.springstudy.domain.like.service.LikeService;
import com.dku.springstudy.domain.product.Product;
import com.dku.springstudy.domain.product.service.ProductService;
import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/{productId}/addLike")
    public ResponseEntity<?> addLike(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable("productId") Long productId) {
        User user = userService.findUser(userDetails.getUsername());
        Product product = productService.findProduct(productId);

        likeService.addLike(new Likes(productId, user, product));
        productService.addLikeCount(product);

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @DeleteMapping("/{productId}/deleteLike")
    public ResponseEntity<?> deleteLike(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable("productId") Long productId) {
        User user = userService.findUser(userDetails.getUsername());
        likeService.deleteLike(productId, user.getId());
        productService.deleteLikeCount(productService.findProduct(productId));

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
