package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import ai.platform.proma.domain.enums.BlockCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListPromptAtom {
    private Long blockId;

    public static PromptBlock toEntity(Prompt prompt, Block block) {
        return PromptBlock.builder()
                .prompt(prompt)
                .block(block)
                .build();
    }
}
