package com.dku.springstudy.controller;

import com.dku.springstudy.dto.category.response.CategoryDeleteResponseDto;
import com.dku.springstudy.dto.category.response.CategoryGetResponseDto;
import com.dku.springstudy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category/get/list")
    public List<CategoryGetResponseDto> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/category/get/{category}")
    public CategoryGetResponseDto getPost(@PathVariable("category") String category){
        return categoryService.getCategory(category);
    }

    @DeleteMapping("/category/delete/{category}")
    public CategoryDeleteResponseDto deletePost(@PathVariable("category") String category){
        return categoryService.deleteCategory(category);
    }
}
