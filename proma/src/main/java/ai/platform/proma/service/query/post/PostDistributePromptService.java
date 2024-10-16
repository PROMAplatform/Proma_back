package ai.platform.proma.service.query.post;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PostRepository;
import ai.platform.proma.repository.PromptBlockRepository;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.post.PostDistributePromptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDistributePromptService implements PostDistributePromptUseCase {

    private final PromptRepository promptRepository;
    private final PostRepository postRepository;
    private final PromptBlockRepository promptBlockRepository;
    private final UserRepository userRepository;
    public Boolean distributePrompt(Long userId, Long promptId, PostDistributeRequestDto postDistributeRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findById(promptId)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));


        Prompt prompt1 = promptRepository.save(Prompt.distributePrompt(prompt, user));

        postRepository.save(postDistributeRequestDto.toEntity(prompt1));

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(prompt);

        List<PromptBlock> newPromptBlocks = promptBlocks.stream()
                .map(promptBlock -> PromptBlock.scrapPromptBlock(prompt1, promptBlock.getBlock()))
                .toList();
        promptBlockRepository.saveAll(newPromptBlocks);


        return true;
    }
}
