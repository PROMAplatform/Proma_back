package ai.platform.proma.usecase.prompt;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PromptSaveRequestDto;

@UseCase
public interface PromptMakePromptUseCase {
    Boolean makePrompt(PromptSaveRequestDto promptSaveRequestDto, User user);
}
