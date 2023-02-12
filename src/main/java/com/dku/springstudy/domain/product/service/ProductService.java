package com.dku.springstudy.domain.product.service;

import com.dku.springstudy.domain.product.Product;
import com.dku.springstudy.domain.product.dto.ProductRegisterRequestDTO;
import com.dku.springstudy.domain.product.repository.ProductRepository;
import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
