package ai.platform.proma.usecase.prompt;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.BlockSaveRequestDto;

@UseCase
public interface PromptMakeBlockUseCase {
    Boolean makeBlock(BlockSaveRequestDto blockSaveRequestDto, User user);
}
