package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;

import java.util.Map;

@UseCase
public interface PostGetPostsPreviewUseCase  {
    Map<String, Object> getPostsPreview(String searchKeyword, String category, int page, int size, String likeOrder, String method);
}
