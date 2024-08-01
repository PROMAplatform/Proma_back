package ai.platform.proma.controller;

import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.PromptListResponseDto;
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
        @RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto,
        @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.saveChatRoom(chatRoomSaveRequestDto, userId));
    }

    @GetMapping("/room/list")
    public ResponseDto<Map<String, List<ChatRoomListResponseDto>>> chatRoomList(
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.getChatRoomList(userId));
    }

    @PatchMapping("/room/emoji/{chatRoomId}")
    public ResponseDto<ChatRoomIdResponseDto> updateChatRoomEmoji(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @RequestParam("userId") Long userId

            ) {
        return new ResponseDto<>(chatRoomSidebarService.updateChatRoomEmoji(chatRoomId, updateEmojiRequestDto, userId));
    }

    @DeleteMapping("/room/{chatRoomId}")
    public ResponseDto<Boolean> deleteChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.deleteChatRoom(chatRoomId, userId));
    }

    @PatchMapping("/prompt/{promptId}")
    public ResponseDto<Boolean> updatePromptDetail(
            @RequestBody PromptDetailUpdateRequestDto promptDetailUpdateRequestDto,
            @PathVariable("promptId") Long promptId,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.updatePromptDetail(promptDetailUpdateRequestDto, promptId, userId));
    }

    @DeleteMapping("/prompt/{promptId}")
    public ResponseDto<Boolean> deletePrompt(
            @PathVariable("promptId") Long promptId,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.deletePrompt(promptId, userId));
    }

    @GetMapping("/prompt/list")
    public ResponseDto<Map<String, List<PromptListResponseDto>>> promptList(
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.getPromptList(userId));
    }

    @PatchMapping("/prompt/emoji/{promptId}")
    public ResponseDto<ChatRoomIdResponseDto> updatePromptEmoji(
            @PathVariable("promptId") Long promptId,
            @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomSidebarService.updatePromptEmoji(promptId, updateEmojiRequestDto, userId));
    }

}
