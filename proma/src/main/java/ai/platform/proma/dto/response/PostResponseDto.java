package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@ToString
public class PostResponseDto {

    private final Long id;

    private final String postTitle;

    private final String postDescription;

    private final LocalDate createAt;

    private final Long promptId;

    private final int likeCount;

    @Builder
    public PostResponseDto(Post post, int likeCount) {
        this.id = post.getId();
        this.postTitle = post.getPostTitle();
        this.postDescription = post.getPostDescription();
        this.createAt = post.getCreateAt();
        this.promptId = post.getPrompt().getId();
        this.likeCount = likeCount;

    }
}
