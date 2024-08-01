package ai.platform.proma.dto.request;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomSaveRequestDto {
    private Long userId;
    private String roomTitle;
    private String emoji;

    public ChatRoom toEntity(ChatRoomSaveRequestDto chatRoomSaveRequestDto, User user) {
        return ChatRoom.builder()
                .user(user)
                .chatRoomTitle(chatRoomSaveRequestDto.getRoomTitle())
                .emoji(chatRoomSaveRequestDto.getEmoji())
                .build();
    }
}
