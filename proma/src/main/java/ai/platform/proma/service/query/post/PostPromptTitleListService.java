package ai.platform.proma.service.query.post;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PromptTitleList;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.usecase.post.PostPromptTitleListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostPromptTitleListService implements PostPromptTitleListUseCase {

    private final PromptRepository promptRepository;

    public Map<String, List<PromptTitleList>> promptTitleList(User user) {

        List<Prompt> prompts = promptRepository.findByUserAndScrap(user);

        Map<String, List<PromptTitleList>> response = new HashMap<>();

        response.put("promptList", prompts.stream()
                .map(PromptTitleList::of)
                .collect(Collectors.toList()));

        return response;
    }
}
