package ai.platform.proma.dto.request;

import ai.platform.proma.domain.enums.PromptCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record PromptDetailUpdateRequestDto(
        String promptTitle,
        String promptDescription,
        String promptCategory,
        String promptPreview){

}
