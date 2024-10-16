package ai.platform.proma.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "LIKE_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate likeAt;

    // --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Like(Long id, LocalDate likeAt, User user, Post post) {
        this.id = id;
        this.likeAt = likeAt;
        this.user = user;
        this.post = post;
    }

    public static Like postLike(Post post, User user) {
        return Like.builder()
                .post(post)
                .likeAt(LocalDate.now())
                .user(user)
                .build();
    }
}
