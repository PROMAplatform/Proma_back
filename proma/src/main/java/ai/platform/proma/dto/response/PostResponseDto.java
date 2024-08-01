package ai.platform.proma.dto.response;


import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
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

    private final String promptMethod;

    private final String promptPreview;

    private final String promptCategory;

    private final Boolean likeState;

    @Builder
    public PostResponseDto(Post post, int likeCount, Boolean likeState) {
        this.postId = post.getId();
        this.postTitle = post.getPostTitle();
        this.postDescription = post.getPostDescription();
        this.userName = post.getPrompt().getUser().getUserName();
        this.createAt = post.getCreateAt();
        this.promptId = post.getPrompt().getId();
        this.likeCount = likeCount;
        this.promptMethod = post.getPrompt().getPromptMethods().getPromptMethod().toString();
        this.promptCategory = post.getPrompt().getPromptCategory().toString();
        this.promptPreview = post.getPrompt().getPromptPreview();
        this.likeState = likeState;
    }


}
