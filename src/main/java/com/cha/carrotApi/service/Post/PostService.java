package com.cha.carrotApi.service.Post;

import com.cha.carrotApi.DTO.post.*;
import com.cha.carrotApi.domain.Category.Category;
import com.cha.carrotApi.domain.Post.Image;
import com.cha.carrotApi.domain.Post.InterestedPost;
import com.cha.carrotApi.domain.Post.LikePost;
import com.cha.carrotApi.domain.Post.Post;
import com.cha.carrotApi.domain.User.User;
import com.cha.carrotApi.exception.CategoryNotFoundException;
import com.cha.carrotApi.exception.InterestedNotFoundException;
import com.cha.carrotApi.exception.PostNotFoundException;
import com.cha.carrotApi.exception.UserNotEqualsException;
import com.cha.carrotApi.repository.Category.CategoryRepository;
import com.cha.carrotApi.repository.Post.InterestedRepository;
import com.cha.carrotApi.repository.Post.LikeRepository;
import com.cha.carrotApi.repository.Post.PostRepository;
import com.cha.carrotApi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final static String SUCCESS_LIKE_POST = "좋아요 처리";
    private final static String SUCCESS_UNLIKE_POST = "좋아요 취소";
    private final static String SUCCESS_INTEREST_POST = "관심글 처리";
    private final static String SUCCESS_NOT_INTEREST_POST = "관심글 취소";

    private final PostRepository postRepository;
    private final FileService fileService;
    private final LikeRepository likeRepository;
    private final InterestedRepository interestedRepository;
    private final int RECOMMEND_SET_COUNT = 10;
    private final CategoryRepository categoryRepository;

    //글 생성
    @Transactional
    public PostCreateResponse createPost(PostCreateRequest req, int categoryId, User user) {
        List<Image> images = req.getImages().stream()
                .map(i -> new Image(i.getOriginalFilename()))
                .collect(toList());
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Post post = postRepository.save(new Post(user, req.getTitle(), req.getContent(), category, images));
        uploadImages(post.getImages(), req.getImages());
        return new PostCreateResponse(post.getId(), post.getTitle(), post.getContent());
    }

    //전체 글 조회
    @Transactional(readOnly = true)
    public PostFindAll findAllPosts(Integer page, int categoryId) {
        Page<Post> posts = makePagePosts(page, categoryId);
        return responsePagingPosts(posts);
    }

    private PostFindAll responsePagingPosts(Page<Post> posts) {
        List<PostSimpleDto> postSimpleDtoList = posts.stream()
                .map(i -> new PostSimpleDto().toDto(i))
                .collect(toList());
        return PostFindAll.toDto(postSimpleDtoList, new PageInfoDto(posts));
    }

    private Page<Post> makePagePosts(Integer page, int categoryId) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Post> posts = postRepository.findAllByCategoryId(pageRequest, categoryId);
        return posts;
    }

    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size())
                .forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
    }

    private void deleteImages(List<Image> images) {
        images.forEach(i -> fileService.delete(i.getUniqueName()));
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        User user = post.getUser();
        return PostResponseDto.toDto(post, user.getNickname());
    }

    @Transactional
    public String updateLikeOfPost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        if (!likeRepository.findByPostAndUser(post, user).isPresent()) {
            post.increaseLikeCount();
            return createLikePost(post, user);
        }
        post.decreaseLikeCount();
        return removeLikePost(post, user);
    }
    @Transactional
    public String updateInterestedPost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        if (!interestedRepository.findByPostAndUser(post, user).isPresent()) {
            post.increaseInterested();
            return createInterestedPost(post, user);
        }
        post.decreaseInterested();
        return removeInterestedPost(post, user);
    }

    @Transactional
    public PostResponseDto editPost(Long id, PostUpdateRequest req, User user) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        validatePostOwner(user, post);
        Post.ImageUpdatedResult result = post.update(req);
        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
        deleteImages(result.getDeletedImages());
        return PostResponseDto.toDto(post, user.getNickname());
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        validatePostOwner(user, post);
        postRepository.delete(post);
    }

    @Transactional
    public List<PostSimpleDto> searchPost(String keyword, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContaining(keyword, pageable);
        List<PostSimpleDto> postSimpleDtoList = posts.stream()
                .map(i -> new PostSimpleDto().toDto(i))
                .collect(toList());
        return postSimpleDtoList;
    }


    private void validatePostOwner(User user, Post post) {
        if (!user.equals(post.getUser())) {
            throw new UserNotEqualsException();
        }
    }

    public String createLikePost(Post board, User user) {
        LikePost likePost = new LikePost(board, user); // true 처리
        likeRepository.save(likePost);
        return SUCCESS_LIKE_POST;
    }

    private String removeLikePost(Post post, User user) {
        LikePost likePost = likeRepository.findByPostAndUser(post, user).orElseThrow(() -> {
            throw new IllegalArgumentException("'좋아요' 기록을 찾을 수 없습니다.");
        });
        likeRepository.delete(likePost);
        return SUCCESS_UNLIKE_POST;
    }

    private String createInterestedPost(Post post, User user) {
        InterestedPost interestedPost = new InterestedPost(post, user);
        interestedRepository.save(interestedPost);
        return SUCCESS_INTEREST_POST;
    }

    private String removeInterestedPost(Post post, User user) {
        InterestedPost interestedPost = interestedRepository.findByPostAndUser(post, user)
                .orElseThrow(InterestedNotFoundException::new);
        interestedRepository.delete(interestedPost);
        return SUCCESS_NOT_INTEREST_POST;
    }



}
