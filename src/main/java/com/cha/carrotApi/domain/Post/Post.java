package com.cha.carrotApi.domain.Post;

import com.cha.carrotApi.DTO.post.PostUpdateRequest;
import com.cha.carrotApi.domain.BaseTimeEntity;
import com.cha.carrotApi.domain.Category.Category;
import com.cha.carrotApi.domain.User.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Column(name = "INTEREST_COUNT")
    private int interested;

    @Column(name = "POST_STATUS")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    @ManyToMany
//    @JoinTable(name = "INTEREST_POST",
//        joinColumns = @JoinColumn(name = "POST_ID"),
//        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
//    private List<User> interest_posts = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> images;

    public Post(User user, String title, String content, Category category, List<Image> images) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.likeCount = 0;
        this.interested = 0;
        this.postStatus = PostStatus.ITEM_SELLING;
        this.category = category;
        this.images = new ArrayList<>();
        addImages(images);
    }


    public ImageUpdatedResult update(PostUpdateRequest req) {
        this.title = title;
        this.content = content;
        ImageUpdatedResult result = findImageUpdatedResult(req.getAddedImages(), req.getDeletedImages());
        addImages(result.getAddedImages());
        deleteImages(result.getDeletedImages());
        return result;
    }

    private void deleteImages(List<Image> deleted) {
        deleted.stream().forEach(di -> this.images.remove(di));
    }

    private void addImages(List<Image> added) {
        added.stream().forEach(i -> {
            images.add(i);
            i.initPost(this);
        });
    }

    private ImageUpdatedResult findImageUpdatedResult(List<MultipartFile> addedImagesFiles, List<Integer> deletedImagesIds) {
        List<Image> addedImages = convertImageFilesToImages(addedImagesFiles);
        List<Image> deletedImages = convertImageIdsToImages(deletedImagesIds);
        return new ImageUpdatedResult(addedImagesFiles, addedImages, deletedImages);
    }

    private List<Image> convertImageIdsToImages(List<Integer> imageIds) {
        return imageIds.stream()
                .map(id -> convertImageIdToImages(id))
                .filter(i -> i.isPresent())
                .map(i -> i.get())
                .collect(toList());
    }

    private Optional<Image> convertImageIdToImages(int id) {
        return this.images.stream().filter(i -> i.getId() == (id)).findAny();
    }


    private List<Image> convertImageFilesToImages(List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(imageFile -> new Image(imageFile.getOriginalFilename())).collect(toList());
    }

    @Getter
    @AllArgsConstructor
    public static class ImageUpdatedResult {
        private List<MultipartFile> addedImageFiles;
        private List<Image> addedImages;
        private List<Image> deletedImages;
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }
    public void decreaseLikeCount() {
        this.likeCount -= 1;
    }

    public void increaseInterested() {
        this.interested += 1;
    }
    public void decreaseInterested() {
        this.interested -= 1;
    }
}
