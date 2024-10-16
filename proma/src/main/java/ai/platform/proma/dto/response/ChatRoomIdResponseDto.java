package ai.platform.proma.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomIdResponseDto {
    private Long roomId;

    public static ChatRoomIdResponseDto of(Long roomId) {
        return ChatRoomIdResponseDto.builder()
                .roomId(roomId)
                .build();
    }
}
