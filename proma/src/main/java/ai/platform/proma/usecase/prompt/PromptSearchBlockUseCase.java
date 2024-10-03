package ai.platform.proma.usecase.prompt;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.SelectBlockDto;

import java.util.List;
import java.util.Map;

@UseCase
public interface PromptSearchBlockUseCase {
    Map<String, List<SelectBlockDto>> searchBlock(User user, String promptMethod);
}
