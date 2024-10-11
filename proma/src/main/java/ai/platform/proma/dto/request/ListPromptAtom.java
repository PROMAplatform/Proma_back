package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import jakarta.validation.constraints.NotNull;

public record ListPromptAtom(
        @NotNull(message = "blockId must not be null")
        Long blockId
) {
    public static PromptBlock toEntity(Prompt prompt, Block block) {
        return PromptBlock.builder()
                .prompt(prompt)
                .block(block)
                .build();
    }
}
