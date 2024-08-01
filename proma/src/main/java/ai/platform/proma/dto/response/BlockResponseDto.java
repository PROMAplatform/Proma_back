package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.enums.BlockCategory;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlockResponseDto {

    private final Long blockId;
    private final String blockTitle;
    private final String blockCategory;


    public BlockResponseDto(Block block) {
        this.blockId = block.getId();
        this.blockTitle = block.getBlockValue();
        this.blockCategory = block.getBlockCategory().toString();
    }
}
