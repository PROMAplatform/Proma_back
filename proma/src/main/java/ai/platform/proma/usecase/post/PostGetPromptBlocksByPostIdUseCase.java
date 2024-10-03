package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;

import java.util.Map;

@UseCase
public interface PostGetPromptBlocksByPostIdUseCase {
    Map<String, Object> getPromptBlocksByPostId(Long postId);
}
