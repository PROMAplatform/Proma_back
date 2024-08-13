package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.enums.BlockCategory;
import ai.platform.proma.domain.enums.BlockDescription;
import ai.platform.proma.domain.enums.BlockValue;
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
    private Boolean isDefault;


    public static SelectBlockDto of(Block block) {
         return SelectBlockDto.builder()
                .blockId(block.getId())
                .blockValue(BlockValue.fromValue(block.getBlockValue()))
                .blockDescription(BlockDescription.fromValue(block.getBlockDescription()))
                .blockCategory(block.getBlockCategory().toString())
                .build();
    }

    public static SelectBlockDto ofDefault(Block block, Boolean isDefault) {
        return SelectBlockDto.builder()
                .blockId(block.getId())
                .blockValue(BlockValue.fromValue(block.getBlockValue()))
                .blockDescription(BlockDescription.fromValue(block.getBlockDescription()))
                .blockCategory(block.getBlockCategory().toString())
                .isDefault(isDefault)
                .build();
    }
}

