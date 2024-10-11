package ai.platform.proma.dto.request;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record ChatRoomSaveRequestDto(
        String roomTitle,
        String emoji) {

    public ChatRoom toEntity(User user) {
        return ChatRoom.builder()
                .user(user)
                .chatRoomTitle(this.roomTitle)
                .emoji(this.emoji)
                .build();
    }
}
