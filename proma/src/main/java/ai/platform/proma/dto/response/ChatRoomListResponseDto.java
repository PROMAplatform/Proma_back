package ai.platform.proma.dto.response;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import lombok.*;


@Builder
@Getter
@NoArgsConstructor
public class ChatRoomListResponseDto {
    private Long roomId;
    private String emoji;
    private String chatRoomTitle;

    @Builder
    public ChatRoomListResponseDto(Long roomId, String emoji, String chatRoomTitle) {
        this.roomId = roomId;
        this.emoji = emoji;
        this.chatRoomTitle = chatRoomTitle;
    }

    public static ChatRoomListResponseDto of(ChatRoom chatRoom) {
        return ChatRoomListResponseDto.builder()
                .roomId(chatRoom.getId())
                .emoji(chatRoom.getEmoji())
                .chatRoomTitle(chatRoom.getChatRoomTitle())
                .build();
    }
}
