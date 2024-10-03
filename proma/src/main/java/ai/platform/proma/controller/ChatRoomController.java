package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PromptUpdateRequestDto;
import ai.platform.proma.dto.response.MessageListResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.service.ChatRoomService;
import ai.platform.proma.usecase.chatroom.ChatRoomEnterChatRoomUseCase;
import ai.platform.proma.usecase.chatroom.ChatRoomUpdatePromptBlockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatRoomEnterChatRoomUseCase chatRoomEnterChatRoomUseCase;
    private final ChatRoomUpdatePromptBlockUseCase chatRoomUpdatePromptBlockUseCase;

    @GetMapping("/{chatRoomId}")
    public ResponseDto<Map<String, List<MessageListResponseDto>>> enterChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomEnterChatRoomUseCase.enterChatRoom(chatRoomId, user));
    }

    @PatchMapping("/prompt/block/{promptId}")
    public ResponseDto<Boolean> blockPrompt(
            @PathVariable("promptId") Long promptId,
            @LoginUser User user,
            @RequestBody PromptUpdateRequestDto promptUpdateRequestDto
            ) {
        return new ResponseDto<>(chatRoomUpdatePromptBlockUseCase.updatePromptBlock(promptUpdateRequestDto, promptId, user));
    }
}
