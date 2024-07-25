package ai.platform.proma.service;
import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.BlockResponseDto;
import ai.platform.proma.dto.response.PostResponseDto;
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
    public Page<PostResponseDto> getPostsByUserLikes(Long userId, PromptCategory category, Pageable pageable, String likeOrder, String latestOrder) {
        userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<Long> postIds = likeRepository.findPostIdsByUserId(userId);

        Page<Post> posts = postRepository.findAllByPostIdInAndPromptCategory(category, postIds, pageable);

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
    public Page<PostResponseDto> getPostsByUserDistribute(Long userId, PromptCategory category, Pageable pageable, String likeOrder, String latestOrder) {
        userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

//        List<Long> postIds = likeRepository.findPostIdsByUserId(userId);

        Page<Post> posts = postRepository.findAllByPromptUserId(userId, pageable);

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

        for (PromptBlock promptBlock : promptBlocks) {
            // 기존 Block 조회 (title, blockDescription, blockCategory 기준)
            Block existingBlock = blockRepository.findByBlockValueAndBlockDescriptionAndBlockCategoryAndUserId(
                    promptBlock.getBlock().getBlockValue(),
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

    public List<BlockResponseDto> getPromptBlocksByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(post.getPrompt()); // PromptBlock 조회

        return promptBlocks.stream()
                .map(Block -> new BlockResponseDto(Block.getBlock())) // Block 정보만 추출하여 DTO 생성
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean postLike(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        User user = userRepository.findById(post.getPrompt().getUser().getId()) // post -> prompt -> userId
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        // 좋아요를 눌렀는지 확인
        boolean isLiked = likeRepository.existsByPostAndUser(post, user);

        if (isLiked) {
            // 이미 좋아요를 눌렀다면 취소
            likeRepository.deleteByPostAndUser(post, user);
            return true; // 좋아요 취소 결과 반환
        } else {
            // 좋아요를 누르지 않았다면 추가
            Like like = Like.postLike(post, user);
            likeRepository.save(like);
            return true; // 좋아요 추가 결과 반환
        }
    }
    @Transactional
    public Boolean updatePost(Long userId, Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));
        userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        if (!post.getPrompt().getUser().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }

        post.update(postRequestDto.getPostTitle(), postRequestDto.getPostDescription());

        return true;
    }

    @Transactional
    public Boolean deletePost(Long userId, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));
        userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        if (!post.getPrompt().getUser().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }

        postRepository.delete(post);
        return true;
    }

}
