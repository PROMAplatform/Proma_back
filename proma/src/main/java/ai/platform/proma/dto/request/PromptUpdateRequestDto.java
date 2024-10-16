package ai.platform.proma.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public record PromptUpdateRequestDto(
        @NotNull(message = "promptPreview must not be null")
        String promptPreview,
        @NotNull(message = "listPromptAtom must not be null")
        List<ListPromptAtom> listPromptAtom
) {


}
