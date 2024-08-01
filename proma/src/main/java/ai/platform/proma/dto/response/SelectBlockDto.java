package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.enums.BlockCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Builder
@Getter
public class SelectBlockDto {
    private Long blockId;
    private String blockValue;
    private String blockDescription;
    private String blockCategory;


    public static SelectBlockDto of(Block block) {
         return SelectBlockDto.builder()
                .blockId(block.getId())
                .blockValue(block.getBlockValue())
                .blockDescription(block.getBlockDescription())
                .blockCategory(block.getBlockCategory().toString())
                .build();
    }
}

