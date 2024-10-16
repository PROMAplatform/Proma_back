package ai.platform.proma.service.command.post;

import ai.platform.proma.domain.*;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.*;
import ai.platform.proma.usecase.post.PostScrapPromptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostScrapPromptService implements PostScrapPromptUseCase {

    private final PromptRepository promptRepository;
    private final PostRepository postRepository;
    private final PromptBlockRepository promptBlockRepository;
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;
    public Boolean scrapPrompt(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        // 새로운 Prompt 생성
        Prompt prompt = Prompt.scrapPost(post, user);
        promptRepository.save(prompt);

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(post.getPrompt());

        List<Block> existingBlocks = blockRepository.findByUserId(user.getId());

        Map<String, Block> blockMap = existingBlocks.stream()
                .collect(Collectors.toMap(
                        b -> b.getBlockValue() + b.getBlockDescription() + b.getBlockCategory(),
                        b -> b
                ));

        promptBlocks.forEach(promptBlock -> {
            String blockKey = promptBlock.getBlock().getBlockValue()
                    + promptBlock.getBlock().getBlockDescription()
                    + promptBlock.getBlock().getBlockCategory();

            Block existingBlock = blockMap.computeIfAbsent(blockKey, k -> {
                Block newBlock = Block.scrapBlock(promptBlock, user);
                blockRepository.save(newBlock);
                return newBlock;
            });

            PromptBlock newPromptBlock = PromptBlock.scrapPromptBlock(prompt, existingBlock);
            promptBlockRepository.save(newPromptBlock);
        });

        return true;
    }
}
