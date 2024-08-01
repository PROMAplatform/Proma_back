package ai.platform.proma.dto.request;

import ai.platform.proma.domain.enums.PromptCategory;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String postTitle;
    private String postDescription;
    private String promptCategory;
}
