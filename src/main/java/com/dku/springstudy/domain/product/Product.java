package com.dku.springstudy.domain.product;

import com.dku.springstudy.domain.BaseTimeEntity;
import com.dku.springstudy.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String productName;
    private String productImgUrl;
    private Integer cost;
    private String contents;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Product(String productName, Category category, Integer cost, String contents,
                   String productImgUrl, User user, ProductStatus productStatus) {
        this.productName = productName;
        this.category = category;
        this.cost = cost;
        this.contents = contents;
        this.productImgUrl = productImgUrl;
        this.status = productStatus;
        this.user = user;
    }


}
