package ai.platform.proma.service.query.post;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.usecase.post.PostPromptDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostPromptDetailService implements PostPromptDetailUseCase {

    private final PromptRepository promptRepository;

    public PromptListResponseDto promptDetail(Long promptId, User user) {

        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        return PromptListResponseDto.of(prompt, prompt.getPromptMethods(), prompt.getPromptBlocks().stream()
                .map(promptBlock -> SelectBlockDto.of(promptBlock.getBlock()))
                .collect(Collectors.toList()));
    }
}
