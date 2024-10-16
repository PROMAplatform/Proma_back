package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

import java.util.Map;

@UseCase
public interface PostGetPostsUseCase {
    Map<String, Object> getPosts(Long userId, String searchKeyword, String category, int page, int size, String likeOrder, String method);
}
