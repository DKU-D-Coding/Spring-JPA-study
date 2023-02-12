package com.cha.carrotApi.domain.Post;

import com.cha.carrotApi.domain.BaseTimeEntity;
import com.cha.carrotApi.domain.User.User;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class InterestedPost extends BaseTimeEntity {
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

    public InterestedPost(Post post, User user) {
        this.post = post;
        this.user = user;
        this.status = true;
    }
}
