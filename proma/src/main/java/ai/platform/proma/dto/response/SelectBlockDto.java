package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.enums.BlockCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class SelectBlockDto {
    private Long blockId;
    private String blockTitle;
    private String blockDescription;
    private BlockCategory blockCategory;


    public static SelectBlockDto of(Block block) {
        return SelectBlockDto.builder()
                .blockId(block.getId())
                .blockTitle(block.getTitle())
                .blockDescription(block.getBlockDescription())
                .blockCategory(block.getBlockCategory())
                .build();
    }
}
