package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record PostDistributeRequestDto(
        String postTitle,
        String postDescription,
        String postCategory
) {

    public Post toEntity(Prompt prompt) {
        return Post.builder()
                .postTitle(this.postTitle)
                .postDescription(this.postDescription)
                .postCategory(PromptCategory.fromValue(this.postCategory))
                .prompt(prompt)
                .build();
    }
}
