package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.BlockCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlockSaveRequestDto {
    private String blockValue;
    private String blockDescription;
    private BlockCategory blockCategory;
    private String promptMethod;

    public Block toEntity(PromptMethods promptMethods, User user, BlockSaveRequestDto blockSaveRequestDto) {
        return Block.builder()
                .user(user)
                .blockValue(blockSaveRequestDto.getBlockValue())
                .blockDescription(blockSaveRequestDto.getBlockDescription())
                .blockCategory(blockSaveRequestDto.getBlockCategory())
                .promptMethods(promptMethods)
                .build();
    }
}
