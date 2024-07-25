package ai.platform.proma.dto.response;

import ai.platform.proma.domain.CommunicationMethod;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.Scrap;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private final String promptType;

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
        this.promptType = post.getPrompt().getCommunicationMethod().getType();
        this.promptCategory = post.getPrompt().getPromptCategory();
        this.promptPreview = post.getPrompt().getPromptPreview();
    }
}
