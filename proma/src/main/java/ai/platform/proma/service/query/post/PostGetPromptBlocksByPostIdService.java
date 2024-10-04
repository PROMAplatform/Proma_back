package ai.platform.proma.service.query.post;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.PromptBlock;
import ai.platform.proma.dto.response.BlockResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PostRepository;
import ai.platform.proma.repository.PromptBlockRepository;
import ai.platform.proma.usecase.post.PostGetPromptBlocksByPostIdUseCase;
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
public class PostGetPromptBlocksByPostIdService implements PostGetPromptBlocksByPostIdUseCase {

    private final PostRepository postRepository;
    private final PromptBlockRepository promptBlockRepository;

    public Map<String, Object> getPromptBlocksByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(post.getPrompt()); // PromptBlock 조회
        List<BlockResponseDto> blockResponseDtoList=  promptBlocks.stream()
                .map(Block -> new BlockResponseDto(Block.getBlock())) // Block 정보만 추출하여 DTO 생성
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("selectPromptAtom", blockResponseDtoList);

        return response;
    }
}
