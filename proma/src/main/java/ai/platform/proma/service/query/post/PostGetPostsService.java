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
import ai.platform.proma.usecase.post.PostGetPostsUseCase;
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
public class PostGetPostsService implements PostGetPostsUseCase {

    private final PostRepository postRepository;
    private final CommunicationMethodRepository communicationMethodRepository;
    private final CreateResultMapImpl createResultMap;
    private final SortOrderImpl sortOrder;

    public Map<String, Object> getPosts(User user, String searchKeyword, String category, int page, int size, String likeOrder, String method) {

        Pageable pageable = PageRequest.of(page, size, sortOrder.execute(likeOrder));

        PromptMethods promptMethods = null;
        if(!method.isEmpty()) {
            promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(method))
                    .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        }

        Page<SortInfo> sortInfoPage = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, PromptCategory.fromValue(category), promptMethods, pageable);

        return createResultMap.execute(sortInfoPage, user);
    }

}
