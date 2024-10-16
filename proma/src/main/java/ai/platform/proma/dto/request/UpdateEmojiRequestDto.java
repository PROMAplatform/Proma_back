package ai.platform.proma.dto.request;

import jakarta.validation.constraints.NotNull;


public record UpdateEmojiRequestDto(
        @NotNull(message = "emoji must not be null")
        String emoji
) {

}
