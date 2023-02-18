package com.dku.springstudy.service;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.domain.Post;
import com.dku.springstudy.dto.category.response.CategoryDeleteResponseDto;
import com.dku.springstudy.dto.category.response.CategoryGetResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private void createCategory(String category){

        Category newCategory = Category.builder()
                .category(category)
                .created(new Timestamp(System.currentTimeMillis()))
                .build();

        categoryRepository.save(newCategory);
    }

    public List<CategoryGetResponseDto> getAllCategory(){

        List<Category> categoryList = categoryRepository.findAll()
               .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        return categoryList.stream().map(CategoryGetResponseDto::new).collect(Collectors.toList());
    }

    public CategoryGetResponseDto getCategory(String category){

        Category findCategory = categoryRepository.findByCategory(category)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        return new CategoryGetResponseDto(findCategory);
    }

    public Category findCategory(String category){

        Optional<Category> findCategory = categoryRepository.findByCategory(category);

        if(findCategory.isPresent()){
            return findCategory.get();
        }else{
            createCategory(category);
            return findCategory(category);
        }

    }

    public CategoryDeleteResponseDto deleteCategory(String category){

        Category findCategory = categoryRepository.findByCategory(category)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        try{
            categoryRepository.remove(findCategory);
            return new CategoryDeleteResponseDto(true);
        }catch (Exception e){
            return new CategoryDeleteResponseDto(false,e.getMessage());
        }

    }

}
