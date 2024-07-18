package ai.platform.proma.service;
import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.PostResponseDto;
import ai.platform.proma.dto.response.PromptResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    private final PromptBlockRepository promptBlockRepository;
    private final BlockRepository blockRepository;

    public Page<PostResponseDto> getPosts(String searchKeyword, PromptCategory category, Pageable pageable, String likeOrder, String latestOrder) {
        Page<Post> posts = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, category, pageable);

        List<PostResponseDto> sortedPostResponseDtos = posts.stream() // posts를 Stream으로 변환
                .map(post -> new PostResponseDto(post, likeRepository.countByPostId(post.getId())))
                .sorted((dto1, dto2) -> {
                    int likeCountComparison = Sort.Direction.fromString(likeOrder).isAscending()
                            ? Integer.compare(dto1.getLikeCount(), dto2.getLikeCount())
                            : Integer.compare(dto2.getLikeCount(), dto1.getLikeCount());

                    if (likeCountComparison == 0) {
                        return Sort.Direction.fromString(latestOrder).isAscending()
                                ? dto1.getCreateAt().compareTo(dto2.getCreateAt())
                                : dto2.getCreateAt().compareTo(dto1.getCreateAt());
                    }
                    return likeCountComparison;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(sortedPostResponseDtos, pageable, posts.getTotalElements());
    }

    @Transactional
    public Boolean scrapPrompt(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = Prompt.scrapPost(post, user);
        promptRepository.save(prompt);

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(post.getPrompt());
//        for (PromptBlock promptBlock : promptBlocks) {
//
//            // Block
//            Block block = Block.scrapBlock(promptBlock, user);
//            blockRepository.save(block);
//
//            // Prompt-Block 매핑
//            PromptBlock newPromptBlock = PromptBlock.scrapPromptBlock(prompt, block);
//            promptBlockRepository.save(newPromptBlock);
//        }
        for (PromptBlock promptBlock : promptBlocks) {
            // 기존 Block 조회 (title, blockDescription, blockCategory 기준)
            Block existingBlock = blockRepository.findByTitleAndBlockDescriptionAndBlockCategoryAndUserId(
                    promptBlock.getBlock().getTitle(),
                    promptBlock.getBlock().getBlockDescription(),
                    promptBlock.getBlock().getBlockCategory(),
                    userId
            ).orElse(null);

            // 기존 Block이 없으면 새로운 Block 생성
            if (existingBlock == null) {
                existingBlock = Block.scrapBlock(promptBlock, user);
                blockRepository.save(existingBlock);
            }

            // PromptBlock 생성 및 저장
            PromptBlock newPromptBlock = PromptBlock.scrapPromptBlock(prompt, existingBlock);
            promptBlockRepository.save(newPromptBlock);
        }

        return true;
    }


}
