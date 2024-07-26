package ai.platform.proma.controller;

import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.ChatRoomUpdateEmojiRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.service.ChatRoomSidebarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting/sidebar")
public class ChatRoomSidebarController {

    private final ChatRoomSidebarService chatRoomSidebarService;

    @PostMapping("/room/save")
    public ResponseDto<ChatRoomIdResponseDto> saveChatRoom(
        @RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto
    ) {
        return new ResponseDto<>(chatRoomSidebarService.saveChatRoom(chatRoomSaveRequestDto));
    }

    @GetMapping("/room/list")
    public ResponseDto<Map<String, List<ChatRoomListResponseDto>>> chatRoomList(
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.getChatRoomList(userId));
    }

    @PatchMapping("/room/emoji/{chatRoomId}")
    public ResponseDto<ChatRoomIdResponseDto> updateEmoji(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody ChatRoomUpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto

            ) {
        return new ResponseDto<>(chatRoomSidebarService.updateEmoji(chatRoomId, chatRoomUpdateEmojiRequestDto));
    }

    @DeleteMapping("/room/{chatRoomId}")
    public ResponseDto<Boolean> deleteChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.deleteChatRoom(chatRoomId, userId));
    }


}
