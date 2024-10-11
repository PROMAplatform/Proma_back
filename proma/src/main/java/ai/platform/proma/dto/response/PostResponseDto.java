package ai.platform.proma.dto.response;


import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class PostResponseDto {

    private Long postId;

    private String postTitle;

    private String postDescription;

    private LocalDateTime createAt;

    private String userName;

    private Long promptId;

    private int likeCount;

    private String promptMethod;

    private String promptPreview;

    private String postCategory;

    private Boolean likeState;

    public static PostResponseDto of(SortInfo sortInfo, Boolean likeState) {
        Post post = sortInfo.getPost();
        return PostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .postDescription(post.getPostDescription())
                .userName(post.getPrompt().getUser().getUserName())
                .createAt(post.getCreateAt())
                .promptId(post.getPrompt().getId())
                .likeCount(sortInfo.getLikeCount())
                .likeState(likeState)
                .build();
    }



}
