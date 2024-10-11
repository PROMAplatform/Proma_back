package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import lombok.Getter;

public record PostRequestDto(
        String postTitle,
        String postDescription,
        String postCategory
) {


    public Post toEntity() {
        return Post.builder()
                .postTitle(this.postTitle)
                .postDescription(this.postDescription)
                .postCategory(PromptCategory.fromValue(this.postCategory))
                .build();
    }
}
