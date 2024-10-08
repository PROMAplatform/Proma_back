package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PromptListResponseDto;

@UseCase
public interface PostPromptDetailUseCase {
    PromptListResponseDto promptDetail(Long promptId, Long userId);
}
