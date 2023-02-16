package com.dku.springstudy.domain.product;

import com.dku.springstudy.domain.BaseTimeEntity;
import com.dku.springstudy.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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
    @ColumnDefault("0")
    private Integer likeCount;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Product(String productName, Category category, Integer cost, String contents,
                   String productImgUrl, User user, ProductStatus productStatus, Integer likeCount) {
        this.productName = productName;
        this.category = category;
        this.cost = cost;
        this.contents = contents;
        this.likeCount = likeCount;
        this.productImgUrl = productImgUrl;
        this.status = productStatus;
        this.user = user;
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void deleteLikeCount() {
        this.likeCount--;
    }

}
