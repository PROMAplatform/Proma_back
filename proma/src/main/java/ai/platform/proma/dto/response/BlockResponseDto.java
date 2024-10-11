package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.enums.BlockValue;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class BlockResponseDto {

    private Long blockId;
    private String blockValue;
    private String blockCategory;

    public static BlockResponseDto of (Block block) {
        return BlockResponseDto.builder()
                .blockId(block.getId())
                .blockValue(block.getBlockValue())
                .blockCategory(block.getBlockValue())
                .build();
    }
}
