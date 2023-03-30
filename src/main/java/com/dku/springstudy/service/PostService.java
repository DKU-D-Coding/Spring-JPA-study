package com.dku.springstudy.service;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.domain.Post;
import com.dku.springstudy.domain.PostCategory;
import com.dku.springstudy.dto.post.request.PostCreateRequestDto;
import com.dku.springstudy.dto.post.response.PostCreateResponseDto;
import com.dku.springstudy.dto.post.response.PostDeleteResponseDto;
import com.dku.springstudy.dto.post.response.PostGetResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.CategoryRepository;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostCategoryService postCategoryService;
    private final CategoryService categoryService;

    public PostCreateResponseDto createPost(PostCreateRequestDto postCreateRequestDto, long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_ERROR));

        Post post = Post.builder()
                .member(member)
                .title(postCreateRequestDto.getTitle())
                .content(postCreateRequestDto.getContent())
                .price(postCreateRequestDto.getPrice())
                .created(new Timestamp(System.currentTimeMillis()))
                .build();

        for (String i : postCreateRequestDto.getCategories()){

            Category category = categoryService.findCategory(i);
            postCategoryService.createPostCategory(post, category);
        }

        return new PostCreateResponseDto(post);
    }

    public List<PostGetResponseDto> getAllPost(){
        List<Post> postList = postRepository.findAll()
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND_ERROR));
        List<PostGetResponseDto> result = new ArrayList<>();
        for(Post i : postList){
            result.add(new PostGetResponseDto(i));
        }
        return result;
    }

    public PostGetResponseDto getPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND_ERROR));
        return  new PostGetResponseDto(post);
    }

    public PostDeleteResponseDto deletePost(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND_ERROR));

        try{
            postRepository.remove(post);
            return new PostDeleteResponseDto(true);
        }catch (Exception e){
            return new PostDeleteResponseDto(false,e.getMessage());
        }

    }

}
