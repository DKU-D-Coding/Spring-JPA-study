package com.cha.carrotApi.controller;

import com.cha.carrotApi.DTO.post.PostCreateRequest;
import com.cha.carrotApi.DTO.post.PostUpdateRequest;
import com.cha.carrotApi.domain.User.User;
import com.cha.carrotApi.exception.UserNotFoundException;
import com.cha.carrotApi.repository.User.UserRepository;
import com.cha.carrotApi.response.Response;
import com.cha.carrotApi.service.Post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@Api(value = "Post Controller", tags = "Post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

    @ApiOperation(value = "글 생성", notes = "글을 작성합니다.")
    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createBoard(@Valid @ModelAttribute PostCreateRequest req,
                                @RequestParam(value = "category", defaultValue = "1") int categoryId) {
        // http://localhost:8080/boards?category=3
        User user = getPrincipal();
        return Response.success(postService.createPost(req, categoryId, user));
    }

    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 조회합니다.")
    @GetMapping("/posts/all/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Response findAllPosts(@ApiParam(value = "카테고리 id", required = true) @PathVariable int categoryId, @RequestParam(defaultValue = "0") Integer page) {
        // http://localhost:8080/boards/all/{categoryId}?page=0
        return Response.success(postService.findAllPosts(page, categoryId));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response editBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id,
                              @Valid @ModelAttribute PostUpdateRequest req) {
        User user = getPrincipal();
        return Response.success(postService.editPost(id, req, user));
    }

    @ApiOperation(value = "게시글 좋아요", notes = "사용자가 게시물 좋아요를 누릅니다.")
    @PostMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response likeBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        User user = getPrincipal();
        return Response.success(postService.updateLikeOfPost(id, user));
    }

    @ApiOperation(value = "게시글 관심품목", notes = "사용자가 게시물을 관심품목에 등록합니다.")
    @PostMapping("posts/{id}/favorites")
    @ResponseStatus(HttpStatus.OK)
    public Response favoritePost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        User user = getPrincipal();
        return Response.success(postService.updateInterestedPost(id, user));
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        User user = getPrincipal();
        postService.deletePost(id, user);
        return Response.success();
    }

    @ApiOperation(value = "게시글 검색", notes = "게시글을 검색합니다.")
    @GetMapping("/posts/search")
    @ResponseStatus(HttpStatus.OK)
    public Response searchPost(String keyword,
                               @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        // ex) http://localhost:8080/api/boards/search?page=0
        return Response.success(postService.searchPost(keyword, pageable));
    }




    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByNickname(authentication.getName())
                .orElseThrow(UserNotFoundException::new);
        return user;
    }
}
