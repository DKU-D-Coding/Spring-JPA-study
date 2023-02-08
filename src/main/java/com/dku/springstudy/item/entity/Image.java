package com.dku.springstudy.item.entity;


import com.dku.springstudy.global.BaseEntity;

import javax.persistence.*;

@Entity
public class Image extends BaseEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue
    private String id;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

}
