package ai.platform.proma.service.query.post;

import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.usecase.post.SortOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SortOrderImpl implements SortOrderUseCase {
    public Sort execute(String likeOrder) {
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
}
