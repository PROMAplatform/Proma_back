package ai.platform.proma.controller;

import ai.platform.proma.dto.request.PromptUpdateRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.usecase.chatroom.ChatRoomEnterChatRoomUseCase;
import ai.platform.proma.usecase.chatroom.ChatRoomUpdatePromptBlockUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChatRoomController {

    private final ChatRoomEnterChatRoomUseCase chatRoomEnterChatRoomUseCase;
    private final ChatRoomUpdatePromptBlockUseCase chatRoomUpdatePromptBlockUseCase;

    @GetMapping("/{chatRoomId}")
    public ResponseDto<?> enterChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(chatRoomEnterChatRoomUseCase.enterChatRoom(chatRoomId, userId));
    }

    @PatchMapping("/prompt/block/{promptId}")
    public ResponseDto<Boolean> blockPrompt(
            @PathVariable("promptId") Long promptId,
            @LoginUser Long userId,
            @Valid @RequestBody PromptUpdateRequestDto promptUpdateRequestDto
            ) {
        return new ResponseDto<>(chatRoomUpdatePromptBlockUseCase.updatePromptBlock(promptUpdateRequestDto, promptId, userId));
    }
}
