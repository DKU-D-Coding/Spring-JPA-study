package com.dku.springstudy.domain.product.controller;


import com.dku.springstudy.domain.product.dto.ProductListResponseDTO;
import com.dku.springstudy.domain.product.dto.ProductRegisterRequestDTO;
import com.dku.springstudy.domain.product.dto.ProductResponseDTO;
import com.dku.springstudy.domain.product.service.ProductService;
import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Long> create(@Valid @RequestBody ProductRegisterRequestDTO requestDTO,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User findUser = userService.findUser(userDetails.getUsername());
        Long id = productService.save(requestDTO, findUser);

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> getProduct(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable("productId") Long productId) {
        User loginUser = userService.findUser(userDetails.getUsername());
        ProductResponseDTO view = productService.findByProductId(productId);
        List<ProductResponseDTO> otherList = productService.findByUserId(loginUser.getId());

        return new ResponseEntity<>(new ProductListResponseDTO(view, otherList), HttpStatus.OK);
    }

}
