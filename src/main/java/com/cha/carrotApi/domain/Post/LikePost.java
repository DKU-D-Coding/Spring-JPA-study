package com.cha.carrotApi.domain.Post;

import com.cha.carrotApi.domain.BaseTimeEntity;
import com.cha.carrotApi.domain.User.User;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Builder
public class LikePost extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean status;
    //true = 좋아요

    public LikePost(Post post, User user) {
        this.post = post;
        this.user = user;
        this.status = true;
    }
}
