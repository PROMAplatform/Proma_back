package ai.platform.proma.usecase.prompt;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.dto.request.PromptHistorySaveReuqestDto;

@UseCase
public interface PromptHistorySaveUsecase {
    Boolean execute(Long userId, PromptHistorySaveReuqestDto promptHistorySaveReuqestDto);
}
