package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Like;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPostId(Long postId);
    boolean existsByPostAndUser(Post post, User user); // 좋아요 여부 확인
    void deleteByPostAndUser(Post post, User user); // 좋아요 삭제
    @Query("SELECT l.post.id FROM Like l WHERE l.user.id = :userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);

    boolean existsByPost(Post post); // 로그인 안한 사용자
    @Query("SELECT l.post.id FROM Like l WHERE l.post IN :posts AND l.user = :user")
    List<Long> findLikedPostIdsByPostsAndUser(@Param("posts") List<Post> posts, @Param("user") User user);
}
