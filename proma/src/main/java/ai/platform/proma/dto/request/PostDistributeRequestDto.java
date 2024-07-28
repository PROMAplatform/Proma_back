package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDistributeRequestDto {
    private String postTitle;
    private String postDescription;
    private PromptCategory promptCategory;

    public Post toEntity(Prompt prompt, PostDistributeRequestDto postDistributeRequestDto) {
        return Post.builder()
                .postTitle(postDistributeRequestDto.getPostTitle())
                .postDescription(postDistributeRequestDto.getPostDescription())
                .promptCategory(postDistributeRequestDto.getPromptCategory())
                .prompt(prompt)
                .build();
    }
}
