package ai.platform.proma.usecase.post;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PostDistributeRequestDto;

public interface PostDistributePromptUseCase {
    Boolean distributePrompt(User user, Long promptId, PostDistributeRequestDto postDistributeRequestDto);
}
