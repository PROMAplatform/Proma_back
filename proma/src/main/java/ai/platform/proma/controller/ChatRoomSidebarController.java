package ai.platform.proma.controller;

import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.usecase.chatroom.ChatRoomEnterChatRoomUseCase;
import ai.platform.proma.usecase.chatroom.sidebar.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomSidebarController {

    private final SidebarSaveChatRoomUseCase sidebarSaveChatRoomUseCase;
    private final SidebarGetChatRoomListUseCase sidebarGetChatRoomListUseCase;
    private final SidebarUpdateChatRoomEmojiUseCase sidebarUpdateChatRoomEmojiUseCase;
    private final SidebarDeleteChatRoomUseCase sidebarDeleteChatRoomUseCase;
    private final ChatRoomEnterChatRoomUseCase chatRoomEnterChatRoomUseCase;


    @PostMapping("")
    public ResponseDto<ChatRoomIdResponseDto> saveChatRoom(
            @Valid @RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarSaveChatRoomUseCase.saveChatRoom(chatRoomSaveRequestDto, userId));
    }

    @GetMapping("")
    public ResponseDto<Map<String, List<ChatRoomListResponseDto>>> chatRoomList(
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarGetChatRoomListUseCase.getChatRoomList(userId));
    }

    @PatchMapping("/{chatRoomId}/emojis")
    public ResponseDto<ChatRoomIdResponseDto> updateChatRoomEmoji(
            @PathVariable("chatRoomId") Long chatRoomId,
            @Valid @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarUpdateChatRoomEmojiUseCase.updateChatRoomEmoji(chatRoomId, updateEmojiRequestDto, userId));
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseDto<Boolean> deleteChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarDeleteChatRoomUseCase.deleteChatRoom(chatRoomId, userId));
    }

    @GetMapping("/{chatRoomId}")
    public ResponseDto<?> enterChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(chatRoomEnterChatRoomUseCase.enterChatRoom(chatRoomId, userId));

    }

}
