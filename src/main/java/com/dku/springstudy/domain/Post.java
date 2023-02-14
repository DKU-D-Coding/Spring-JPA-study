package com.dku.springstudy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter //Lombok
@Setter //Lombok
@ToString //Lombok
@Table(name = "Post")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USERID")
    private Member member;
    @Column(name = "LIKECOUNT")
    private int likeCount;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "CREATED")
    private Timestamp created;
    @Column(name = "UPDATED")
    private Timestamp updated;
    @Column(name = "PRICE")
    private int price;
    @JoinColumn(name = "CATEGORYID")
    @Column(name = "STATUS")
    private String status;
}
