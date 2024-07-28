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
    private BlockCategory blockCategory;

//    public static SelectBlockDto off(Block block){ {
//
//        return SelectBlockDto.builder()
//                .blockId(block.getId())
//                .blockValue(block.getBlockValue())
//                .blockDescription(block.getBlockDescription())
//                .blockCategory(block.getBlockCategory())
//                .build();
//    }

    public static SelectBlockDto of(Block block) {
        System.err.println("아이디 "+ block.getId()+ "벨류 "+ block.getBlockValue()+ "설명 "+ block.getBlockDescription()+ "카테고리 "+ block.getBlockCategory());

        return SelectBlockDto.builder()
                .blockId(block.getId())
                .blockValue(block.getBlockValue())
                .blockDescription(block.getBlockDescription())
                .blockCategory(block.getBlockCategory())
                .build();
    }
}

