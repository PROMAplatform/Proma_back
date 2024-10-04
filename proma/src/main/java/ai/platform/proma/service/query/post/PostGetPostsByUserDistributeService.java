package ai.platform.proma.service.query.post;

import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.response.PageInfo;
import ai.platform.proma.dto.response.PostResponseDto;
import ai.platform.proma.dto.response.SortInfo;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.CommunicationMethodRepository;
import ai.platform.proma.repository.LikeRepository;
import ai.platform.proma.repository.PostRepository;
import ai.platform.proma.usecase.post.PostGetPostsByUserDistributeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostGetPostsByUserDistributeService implements PostGetPostsByUserDistributeUseCase {

    private final CommunicationMethodRepository communicationMethodRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

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
}
