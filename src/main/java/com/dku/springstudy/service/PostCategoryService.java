package com.dku.springstudy.service;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.domain.Post;
import com.dku.springstudy.domain.PostCategory;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.CategoryRepository;
import com.dku.springstudy.repository.PostCategoryRepository;
import com.dku.springstudy.repository.PostRepository;
import com.dku.springstudy.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    //private final PostRepository postRepository;
    //private final CategoryRepository categoryRepository;

    public void createPostCategory(Post post, Category category){

        PostCategory postCategory = PostCategory.builder()
                .post(post)
                .category(category)
                .build();
        postCategoryRepository.save(postCategory);

        post.addPostCategory(postCategory);

        category.addPostCategory(postCategory);

    }

}
