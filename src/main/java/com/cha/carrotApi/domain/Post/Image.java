package com.cha.carrotApi.domain.Post;

import com.cha.carrotApi.domain.BaseTimeEntity;
import com.cha.carrotApi.exception.UnsupportedImageFormatException;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String uniqueName;

    @Column(nullable = false)
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    private final static String supportedExtension[] = {"jpg", "jpeg", "gif", "bmp", "png"};

    public Image(String originName) {
        this.originName = originName;
        this.uniqueName = generateUniqueName(extractExtension(originName));
    }

    public void initPost(Post post) {
        if(this.post == null){
            this.post = post;
        }
    }

    private String generateUniqueName(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if(isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException e) { }
        throw new UnsupportedImageFormatException();
    }

    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }
}
