package ai.platform.proma.service;
import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.*;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.*;
import ai.platform.proma.security.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

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
    private final CommunicationMethodRepository communicationMethodRepository;

    private Sort getSortOrder(String likeOrder) {
        return switch (likeOrder != null ? likeOrder.toLowerCase() : "") {
            case "desc" -> Sort.by(
                    new Sort.Order(Sort.Direction.DESC, "likeCount"),
                    new Sort.Order(Sort.Direction.DESC, "createAt"));
            case "" -> Sort.by(
                    new Sort.Order(Sort.Direction.DESC, "createAt"),
                    new Sort.Order(Sort.Direction.DESC, "likeCount"));
            default -> throw new ApiException(ErrorDefine.INVALID_LIKE_ORDER);
        };
    }

    private Map<String, Object> createResultMap(Page<SortInfo> sortInfoPage, User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("selectPrompt", sortInfoPage.getContent().stream()
                .map(sortInfo -> PostResponseDto.of(sortInfo, likeRepository.existsByPostAndUser(sortInfo.getPost(), user)))
                .collect(Collectors.toList()));
        result.put("pageInfo", new PageInfo(sortInfoPage));
        return result;
    }
    public Map<String, List<PromptTitleList>> promptTitleList(User user) {

        List<Prompt> prompts = promptRepository.findByUserAndScrap(user);

        Map<String, List<PromptTitleList>> response = new HashMap<>();

        response.put("promptList", prompts.stream()
                .map(PromptTitleList::of)
                .collect(Collectors.toList()));

        return response;
    }

    public PromptListResponseDto promptDetail(Long promptId, User user) {

        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        return PromptListResponseDto.of(prompt, prompt.getPromptMethods(), prompt.getPromptBlocks().stream()
                .map(promptBlock -> SelectBlockDto.of(promptBlock.getBlock()))
                .collect(Collectors.toList()));
    }
    public Boolean distributePrompt(User user, Long promptId, PostDistributeRequestDto postDistributeRequestDto) {

        Prompt prompt = promptRepository.findById(promptId)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));


        Prompt prompt1 = promptRepository.save(Prompt.distributePrompt(prompt, user));

        postRepository.save(postDistributeRequestDto.toEntity(prompt1, postDistributeRequestDto));

        List<PromptBlock> promptBlocks = promptBlockRepository.findByPrompt(prompt);

        List<PromptBlock> newPromptBlocks = promptBlocks.stream()
                .map(promptBlock -> PromptBlock.scrapPromptBlock(prompt1, promptBlock.getBlock()))
                .toList();
        promptBlockRepository.saveAll(newPromptBlocks);


        return true;
    }

    public Map<String, Object> getPosts(User user, String searchKeyword, String category, int page, int size, String likeOrder, String method) {

        Pageable pageable = PageRequest.of(page, size, getSortOrder(likeOrder));

        PromptMethods promptMethods = null;
        if(!method.isEmpty()) {
            promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(method))
                    .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        }

        Page<SortInfo> sortInfoPage = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, PromptCategory.fromValue(category), promptMethods, pageable);

        return createResultMap(sortInfoPage, user);
    }


    public Map<String, Object> getPostsPreview(String searchKeyword, String category,int page, int size, String likeOrder, String method) {

        Sort sort = getSortOrder(likeOrder);

        PromptMethods promptMethods = null;
        if(!method.isEmpty()) {
            promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(method))
                    .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SortInfo> sortInfoPage = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, PromptCategory.fromValue(category), promptMethods, pageable);

        Map<String, Object> result = new HashMap<>();

        result.put("selectPrompt", sortInfoPage.getContent().stream()
                .map(sortInfo -> PostResponseDto.of(sortInfo, false))
                .collect(Collectors.toList()));
        result.put("pageInfo", new PageInfo(sortInfoPage));

        return result;
    }

    public Map<String, Object> getPostsByUserLikes(User user, String category, int page, int size, String likeOrder, String method) {

        Long userId = user.getId();

        List<Long> postIds = likeRepository.findPostIdsByUserId(userId);

        PromptMethods promptMethods = null;
        if(!method.isEmpty()) {
            promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(method))
                    .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        }

        Sort sort = getSortOrder(likeOrder);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SortInfo> sortInfoPage = postRepository.findAllByPostIdInAndPostCategory(PromptCategory.fromValue(category), promptMethods, postIds, pageable);

        return createResultMap(sortInfoPage, user);

    }
    public Map<String, Object> getPostsByUserDistribute(User user, String category, int page, int size, String likeOrder, String method) {

        Long userId = user.getId();

        PromptMethods promptMethods = null;
        if(!method.isEmpty()) {
            promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(method))
                    .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        }

        Sort sort = getSortOrder(likeOrder);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SortInfo> sortInfoPage = postRepository.findAllByUserIdAndPostCategoryAndIsScrapShared(userId, PromptCategory.fromValue(category), promptMethods, pageable);

        return createResultMap(sortInfoPage, user);

    }

    public Boolean scrapPrompt(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        Long userId = user.getId();

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

    public Boolean postLike(Long postId, User user){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

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
    public Boolean updatePost(User user, Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        Long userId = user.getId();

        if (!post.getPrompt().getUser().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }
        post.update(postRequestDto.toEntity(postRequestDto));

        return true;
    }

    public Boolean deletePost(User user, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        Long userId = user.getId();

        if (!post.getPrompt().getUser().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }

        postRepository.delete(post);
        return true;
    }

}
