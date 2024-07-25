package ai.platform.proma.dto.request;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.BlockCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlockSaveRequestDto {
    private Long userId;
    private String blockTitle;
    private String blockDescription;
    private String blockCategory;

    public Block toEntity(User user, BlockSaveRequestDto blockSaveRequestDto) {
        return Block.builder()
                .user(user)
                .title(blockSaveRequestDto.getBlockTitle())
                .blockDescription(blockSaveRequestDto.getBlockDescription())
                .blockCategory(blockSaveRequestDto.getBlockCategory())
                .build();
    }
}
