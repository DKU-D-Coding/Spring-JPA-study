package com.dku.springstudy.domain.product.service;

import com.dku.springstudy.domain.product.Product;
import com.dku.springstudy.domain.product.dto.ProductRegisterRequestDTO;
import com.dku.springstudy.domain.product.dto.ProductResponseDTO;
import com.dku.springstudy.domain.product.repository.ProductRepository;
import com.dku.springstudy.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long save(ProductRegisterRequestDTO requestDTO, User user) {
        Product product = requestDTO.toEntity(user);
        productRepository.save(product);

        return product.getId();
    }

    public ProductResponseDTO findByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        return new ProductResponseDTO(product);
    }

    public List<ProductResponseDTO> findByUserId(Long userId) {
        List<Product> products = productRepository.findProductByUserId(userId).orElseGet(ArrayList::new);

        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("no id"));
    }

    @Transactional
    public void addLikeCount(Product product) {
        product.addLikeCount();
    }

    @Transactional
    public void deleteLikeCount(Product product) {
        product.deleteLikeCount();
    }

}
