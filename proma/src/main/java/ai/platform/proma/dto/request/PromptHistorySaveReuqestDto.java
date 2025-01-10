package ai.platform.proma.dto.request;

import jakarta.validation.constraints.NotNull;

public record PromptHistorySaveReuqestDto(
        @NotNull(message = "promptHistory must not be null")
        String promptHistory,

        @NotNull(message = "promptMethod must not be null")
        String promptMethod,

        @NotNull(message = "promptCategory must not be null")
        String promptCategory

) {
}
