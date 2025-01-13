package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.BlockCategory;
import ai.platform.proma.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;

public record AiListPromptAtom(
        @NotNull(message = "blockValue must not be null")
        String blockValue,

        @NotNull(message = "blockDescription must not be null")
        String blockDescription,

        @NotNull(message = "BlockCategory must not be null")
        @ValidEnum(enumClass = BlockCategory.class, message = "Invalid block category")
        String blockCategory
) {
}
