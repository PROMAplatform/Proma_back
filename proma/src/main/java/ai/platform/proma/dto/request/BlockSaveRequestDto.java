package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.BlockCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;


public record BlockSaveRequestDto(
        @NotNull(message = "blockValue must not be null")
        String blockValue,

        @NotNull(message = "blockDescription must not be null")
        String blockDescription,

        @NotNull(message = "BlockCategory must not be null")
        @ValidEnum(enumClass = BlockCategory.class, message = "Invalid block category")
        String blockCategory,

        @NotNull(message = "PromptMethod must not be null")
        @ValidEnum(enumClass = PromptMethod.class, message = "Invalid prompt method")
        String promptMethod
) {
    public Block toEntity(PromptMethods promptMethods, User user) {
        return Block.builder()
                .user(user)
                .blockValue(this.blockValue)
                .blockDescription(this.blockDescription)
                .blockCategory(BlockCategory.fromValue(this.blockCategory))
                .promptMethods(promptMethods)
                .build();
    }
}