package ai.platform.proma.service.query.chatroom.sidebar;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarGetPromptListUseCase;
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
public class SidbarGetPromptListService implements SidebarGetPromptListUseCase {

    private final PromptRepository promptRepository;
    private final UserRepository userRepository;
    public Map<String, List<PromptListResponseDto>> getPromptList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<Prompt> prompts = promptRepository.findByUserAndScrap(user);

        Map<String, List<PromptListResponseDto>> promptMap = new HashMap<>();

        promptMap.put("selectPrompt", prompts.stream()
                .map(prompt -> PromptListResponseDto.of(
                        prompt,
                        prompt.getPromptMethods(),
                        prompt.getPromptBlocks().stream()
                                .map(promptBlock -> SelectBlockDto.of(promptBlock.getBlock())).collect(Collectors.toList())))
                .collect(Collectors.toList()));

        return promptMap;
    }
}
