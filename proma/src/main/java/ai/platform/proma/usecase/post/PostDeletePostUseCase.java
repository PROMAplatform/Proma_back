package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

@UseCase
public interface PostDeletePostUseCase {

    Boolean deletePost(Long userId, Long postId);
}
