package ai.platform.proma.dto.request;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import jakarta.validation.constraints.NotNull;



public record ChatRoomSaveRequestDto(
        @NotNull(message = "roomTitle must not be null")
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
