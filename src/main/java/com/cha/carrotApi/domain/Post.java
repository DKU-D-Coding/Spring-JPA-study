package com.cha.carrotApi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Post extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "BOARD_NUMBER")
    private int bno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Column(name = "POST_STATUS")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @Column(name = "CATEGORY")
    @ManyToMany(mappedBy = "category_posts")
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "INTEREST_POST",
        joinColumns = @JoinColumn(name = "POST_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> interest_posts = new ArrayList<>();

    @Column(name = "IMAGE")
    private String image;
}
