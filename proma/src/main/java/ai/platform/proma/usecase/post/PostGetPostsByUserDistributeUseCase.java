package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

import java.util.Map;

@UseCase
public interface PostGetPostsByUserDistributeUseCase {
    Map<String, Object> getPostsByUserDistribute(Long userId, String category, int page, int size, String likeOrder, String method);
}
