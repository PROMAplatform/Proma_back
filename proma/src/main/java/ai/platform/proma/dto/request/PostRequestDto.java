package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String postTitle;
    private String postDescription;
    private String postCategory;

    public Post toEntity(PostRequestDto postRequestDto) {
        return Post.builder()
                .postTitle(postRequestDto.getPostTitle())
                .postDescription(postRequestDto.getPostDescription())
                .postCategory(PromptCategory.fromValue(postRequestDto.getPostCategory()))
                .build();
    }
}
