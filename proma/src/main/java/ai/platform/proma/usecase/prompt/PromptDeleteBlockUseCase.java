package ai.platform.proma.usecase.prompt;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

@UseCase
public interface PromptDeleteBlockUseCase {
    Boolean deleteBlock(Long blockId, User user);
}
