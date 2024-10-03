package ai.platform.proma.repository;

import ai.platform.proma.domain.Like;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPostId(Long postId);
    boolean existsByPostAndUser(Post post, User user); // 좋아요 여부 확인
    void deleteByPostAndUser(Post post, User user); // 좋아요 삭제
    @Query("SELECT l.post.id FROM Like l WHERE l.user.id = :userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);

    boolean existsByPost(Post post); // 로그인 안한 사용자

}
