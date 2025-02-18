package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PostRequestDto;

@UseCase
public interface PostUpdatePostUseCase {
    Boolean updatePost(Long userId, Long postId, PostRequestDto postRequestDto);
}
