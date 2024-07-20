package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Like;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPostId(Long postId);
    boolean existsByPostAndUser(Post post, User user); // 좋아요 여부 확인
    void deleteByPostAndUser(Post post, User user); // 좋아요 삭제
}
