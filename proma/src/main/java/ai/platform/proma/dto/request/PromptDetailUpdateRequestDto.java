package ai.platform.proma.dto.request;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;

public record PromptDetailUpdateRequestDto(
        @NotNull(message = "promptTitle must not be null")
        String promptTitle,
        @NotNull(message = "promptDescription must not be null")
        String promptDescription,
        @NotNull(message = "promptCategory must not be null")
        @ValidEnum(enumClass = PromptCategory.class, message = "Invalid  promptCategory")
        String promptCategory,
        @NotNull(message = "promptPreview must not be null")
        String promptPreview){

}
