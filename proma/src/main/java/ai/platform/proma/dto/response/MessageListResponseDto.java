package ai.platform.proma.dto.response;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MessageListResponseDto {
    private Long messageId;
    private Long promptId;
    private Long chatRoomId;
    private String messageQuestion;
    private String messageAnswer;
    private LocalDate messageCreateAt;
    private String messageFile;


    @Builder
    public MessageListResponseDto(Message message, ChatRoom chatRoom){
        this.messageId = message.getId();
        this.promptId = message.getPrompt() != null? message.getPrompt().getId() : null;
        this.chatRoomId = chatRoom.getId();
        this.messageQuestion = message.getMessageQuestion();
        this.messageAnswer = message.getMessageAnswer();
        this.messageCreateAt = message.getMessageCreateAt();
        this.messageFile = message.getMessageFile() != null ? message.getMessageFile() : null;
    }

    public static MessageListResponseDto of(Message message, ChatRoom chatRoom){
        return MessageListResponseDto.builder()
                .message(message)
                .chatRoom(chatRoom)
                .build();
    }



}
