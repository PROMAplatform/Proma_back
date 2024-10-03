package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

@UseCase
public interface PostScrapPromptUseCase {

    Boolean scrapPrompt(Long postId, User user);
}
