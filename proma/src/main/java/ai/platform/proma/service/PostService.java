package ai.platform.proma.service;
import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.*;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    private final PromptBlockRepository promptBlockRepository;
    private final BlockRepository blockRepository;


    public Map<String, List<PromptTitleList>> promptTitleList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<Prompt> prompts = promptRepository.findByUser(user);

        Map<String, List<PromptTitleList>> response = new HashMap<>();

        response.put("promptList", prompts.stream()
                .map(PromptTitleList::of)
                .collect(Collectors.toList()));

        return response;
    }

    public PromptListResponseDto promptDetail(Long promptId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        return PromptListResponseDto.of(prompt, prompt.getPromptMethods(), prompt.getPromptBlocks().stream()
                .map(promptBlock -> SelectBlockDto.of(promptBlock.getBlock()))
                .collect(Collectors.toList()));
    }
    public Boolean distributePrompt(Long userId, Long promptId, PostDistributeRequestDto postDistributeRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findById(promptId)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        promptRepository.save(Prompt.distributePrompt(prompt, user));
        postRepository.save(postDistributeRequestDto.toEntity(prompt, postDistributeRequestDto));

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(prompt);
        List<PromptBlock> newPromptBlocks = promptBlocks.stream()
                .map(promptBlock -> PromptBlock.scrapPromptBlock(prompt, promptBlock.getBlock()))
                .toList();
        promptBlockRepository.saveAll(newPromptBlocks);


        return true;
    }

    public Map<String, Object> getPosts(Long userId, String searchKeyword, String category, Pageable pageable, String likeOrder, String latestOrder) {
        Page<Post> posts = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, PromptCategory.fromValue(category), pageable);

        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<PostResponseDto> sortedPostResponseDtos = posts.stream()// posts를 Stream으로 변환
                .map(post -> new PostResponseDto(post, likeRepository.countByPostId(post.getId()), likeRepository.existsByPostAndUser(post,user)))
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
        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", sortedPostResponseDtos);
        response.put("pageInfo", new PageInfo(posts));

        return response;
    }

    public Map<String, Object> getPostsPreview(String searchKeyword, String category, Pageable pageable, String likeOrder, String latestOrder) {
        Page<Post> posts = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, PromptCategory.fromValue(category), pageable);

        List<PostResponseDto> sortedPostResponseDtos = posts.stream()// posts를 Stream으로 변환
                .map(post -> new PostResponseDto(post, likeRepository.countByPostId(post.getId()), likeRepository.existsByPost(post)))
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

        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", sortedPostResponseDtos);
        response.put("pageInfo", new PageInfo(posts));

        return response;
    }
    public Map<String, Object> getPostsByUserLikes(Long userId, String category, Pageable pageable, String likeOrder, String latestOrder) {
        userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<Long> postIds = likeRepository.findPostIdsByUserId(userId);

        Page<Post> posts = postRepository.findAllByPostIdInAndPromptCategory(PromptCategory.fromValue(category), postIds, pageable);

        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<PostResponseDto> sortedPostResponseDtos = posts.stream() // posts를 Stream으로 변환
                .map(post -> new PostResponseDto(post, likeRepository.countByPostId(post.getId()), likeRepository.existsByPostAndUser(post,user)))
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
        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", sortedPostResponseDtos);
        response.put("pageInfo", new PageInfo(posts));

        return response;
    }
    public Map<String, Object> getPostsByUserDistribute(Long userId, String category, Pageable pageable, String likeOrder, String latestOrder) {
        userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

//        List<Long> postIds = likeRepository.findPostIdsByUserId(userId);

        Page<Post> posts = postRepository.findAllByPromptUserIdAndPromptCategory(userId, PromptCategory.fromValue(category), pageable);

        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));


        List<PostResponseDto> sortedPostResponseDtos = posts.stream() // posts를 Stream으로 변환
                .map(post -> new PostResponseDto(post, likeRepository.countByPostId(post.getId()), likeRepository.existsByPostAndUser(post,user)))
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
        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", sortedPostResponseDtos);
        response.put("pageInfo", new PageInfo(posts));

        return response;
    }

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

    public Boolean postLike(Long postId, Long userId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        User user = userRepository.findById(userId) // post -> prompt -> userId
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
    public Boolean updatePost(Long userId, Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findById(post.getPrompt().getId())
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        if (!post.getPrompt().getUser().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }
        try {
            prompt.updateCategory(PromptCategory.fromValue(postRequestDto.getPostCategory()));
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorDefine.INVALID_PROMPT_CATEGORY);
        }
        post.update(postRequestDto);

        return true;
    }

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
