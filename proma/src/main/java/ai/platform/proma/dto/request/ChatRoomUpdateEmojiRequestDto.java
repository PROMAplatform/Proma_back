package ai.platform.proma.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomUpdateEmojiRequestDto {
    private Long userId;
    private String emoji;
}
