package ai.platform.proma.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomSaveResponseDto {
    private Long roomId;

    public static ChatRoomSaveResponseDto of(Long roomId) {
        return ChatRoomSaveResponseDto.builder()
                .roomId(roomId)
                .build();
    }
}
