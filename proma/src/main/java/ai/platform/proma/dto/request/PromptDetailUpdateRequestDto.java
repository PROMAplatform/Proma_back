package ai.platform.proma.dto.request;

import ai.platform.proma.domain.enums.PromptCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromptDetailUpdateRequestDto {
    private Long userId;
    private String promptTitle;
    private String promptDescription;
    private PromptCategory promptCategory;
}
