package ai.platform.proma.dto.response;

import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@ToString
public class PostResponseDto {

    private final Long postId;

    private final String postTitle;

    private final String postDescription;

    private final LocalDate createAt;

    private final String userName;

    private final Long promptId;

    private final int likeCount;

    private final PromptMethods promptMethods;

    private final String promptPreview;

    private final PromptCategory promptCategory;

    @Builder
    public PostResponseDto(Post post, int likeCount) {
        this.postId = post.getId();
        this.postTitle = post.getPostTitle();
        this.postDescription = post.getPostDescription();
        this.userName = post.getPrompt().getUser().getUserName();
        this.createAt = post.getCreateAt();
        this.promptId = post.getPrompt().getId();
        this.likeCount = likeCount;
        this.promptMethods = post.getPrompt().getPromptMethods();
        this.promptCategory = post.getPrompt().getPromptCategory();
        this.promptPreview = post.getPrompt().getPromptPreview();
    }
}
