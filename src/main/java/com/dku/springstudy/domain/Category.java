package com.dku.springstudy.domain;

import com.dku.springstudy.dto.post.response.PostGetResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.parameters.P;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "CATEGORY")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    private String category;

    private Timestamp created;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PostCategory> posts = new ArrayList<>();

    public void  addPostCategory(PostCategory postCategory){
        this.posts.add(postCategory);
    }

}
