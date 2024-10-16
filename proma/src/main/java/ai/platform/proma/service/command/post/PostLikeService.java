package ai.platform.proma.service.command.post;

import ai.platform.proma.domain.Like;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.LikeRepository;
import ai.platform.proma.repository.PostRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.post.PostLikeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService implements PostLikeUseCase {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    public Boolean postLike(Long postId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        // 좋아요를 눌렀는지 확인
        boolean isLiked = likeRepository.existsByPostAndUser(post, user);

        if (isLiked) {
            // 이미 좋아요를 눌렀다면 취소
            likeRepository.deleteByPostAndUser(post, user);
            return true; // 좋아요 취소 결과 반환
        } else {
            // 좋아요를 누르지 않았다면 추가
            Like like = Like.postLike(post, user);
            likeRepository.save(like);
            return true; // 좋아요 추가 결과 반환
        }
    }
}
