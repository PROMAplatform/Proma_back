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
import ai.platform.proma.repository.UserRepository;
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
    private final CreateResultMapImpl createResultMap;
    private final SortOrderImpl sortOrder;
    private final UserRepository userRepository;

    public Map<String, Object> getPostsByUserDistribute(Long userId, String category, int page, int size, String likeOrder, String method) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));


        PromptMethods promptMethods = null;
        if(!method.isEmpty()) {
            promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(method))
                    .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        }

        Sort sort = sortOrder.execute(likeOrder);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SortInfo> sortInfoPage = postRepository.findAllByUserIdAndPostCategoryAndIsScrapShared(user.getId(), PromptCategory.fromValue(category), promptMethods, pageable);

        return createResultMap.execute(sortInfoPage, user);

    }

}
