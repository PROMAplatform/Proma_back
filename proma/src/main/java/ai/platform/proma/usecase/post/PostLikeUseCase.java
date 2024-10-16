package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

@UseCase
public interface PostLikeUseCase {
    Boolean postLike(Long postId, Long userId);
}
