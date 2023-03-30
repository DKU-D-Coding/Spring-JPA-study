package com.dku.springstudy.controller;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.post.request.PostCreateRequestDto;
import com.dku.springstudy.dto.post.response.PostCreateResponseDto;
import com.dku.springstudy.dto.post.response.PostDeleteResponseDto;
import com.dku.springstudy.dto.post.response.PostGetResponseDto;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post/create")
    public PostCreateResponseDto createPost(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody PostCreateRequestDto postCreateRequestDto){

        Member member = customUserDetails.getMember();

        return postService.createPost(postCreateRequestDto, member.getMember_id());
    }

    @GetMapping("/post/get/list")
    public List<PostGetResponseDto> getAllPost(){
        return postService.getAllPost();
    }

    @GetMapping("/post/get/{postId}")
    public PostGetResponseDto getPost(@PathVariable("postId") Long postId){
        return postService.getPost(postId);
    }

    @DeleteMapping("/post/delete/{postId}")
    public PostDeleteResponseDto deletePost(@PathVariable("postId") Long postId){
        return postService.deletePost(postId);
    }

}
