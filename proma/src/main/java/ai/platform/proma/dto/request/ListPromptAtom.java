package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record ListPromptAtom(
        Long blockId
) {
    public static PromptBlock toEntity(Prompt prompt, Block block) {
        return PromptBlock.builder()
                .prompt(prompt)
                .block(block)
                .build();
    }
}
