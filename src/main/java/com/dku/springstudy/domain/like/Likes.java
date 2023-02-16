package com.dku.springstudy.domain.like;

import com.dku.springstudy.domain.BaseTimeEntity;
import com.dku.springstudy.domain.product.Product;
import com.dku.springstudy.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long productInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Likes(Long productInfo, User user, Product product) {
        this.productInfo = productInfo;
        this.user = user;
        this.product = product;
    }

}
