package com.dku.springstudy.domain;

import com.dku.springstudy.domain.common.BaseTimeEntity;
import com.dku.springstudy.domain.constant.Category;
import com.dku.springstudy.domain.constant.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    private Product(User user, String title, String content, Integer price, Category category) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.price = price;
        this.category = category;
        this.likeCount = 0;
        this.status = status.PROGRESS;
    }
}
