package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PromptTitleList;

import java.util.List;
import java.util.Map;

@UseCase
public interface PostPromptTitleListUseCase {
    Map<String, List<PromptTitleList>> promptTitleList(User user);
}
