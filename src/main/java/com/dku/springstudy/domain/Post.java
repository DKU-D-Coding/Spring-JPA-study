package com.dku.springstudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter //Lombok
@Setter //Lombok
@Table(name = "POST")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int like_count;

    private String title;

    private String content;

    private Timestamp created;

    private int price;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @Builder.Default
    private List<PostCategory> categories = new ArrayList<>();

    public void addPostCategory(PostCategory postCategory){
        this.categories.add(postCategory);
    }


}
