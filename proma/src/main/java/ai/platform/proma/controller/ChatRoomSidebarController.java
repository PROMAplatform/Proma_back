package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.security.LoginUser;
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
        @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.saveChatRoom(chatRoomSaveRequestDto, user));
    }

    @GetMapping("/room/list")
    public ResponseDto<Map<String, List<ChatRoomListResponseDto>>> chatRoomList(
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.getChatRoomList(user));
    }

    @PatchMapping("/room/emoji/{chatRoomId}")
    public ResponseDto<ChatRoomIdResponseDto> updateChatRoomEmoji(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @LoginUser User user
            ) {
        return new ResponseDto<>(chatRoomSidebarService.updateChatRoomEmoji(chatRoomId, updateEmojiRequestDto, user));
    }

    @DeleteMapping("/room/{chatRoomId}")
    public ResponseDto<Boolean> deleteChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.deleteChatRoom(chatRoomId, user));
    }

    @PatchMapping("/prompt/{promptId}")
    public ResponseDto<Boolean> updatePromptDetail(
            @RequestBody PromptDetailUpdateRequestDto promptDetailUpdateRequestDto,
            @PathVariable("promptId") Long promptId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.updatePromptDetail(promptDetailUpdateRequestDto, promptId, user));
    }

    @DeleteMapping("/prompt/{promptId}")
    public ResponseDto<Boolean> deletePrompt(
            @PathVariable("promptId") Long promptId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.deletePrompt(promptId, user));
    }

    @GetMapping("/prompt/list")
    public ResponseDto<Map<String, List<PromptListResponseDto>>> promptList(
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.getPromptList(user));
    }

    @PatchMapping("/prompt/emoji/{promptId}")
    public ResponseDto<ChatRoomIdResponseDto> updatePromptEmoji(
            @PathVariable("promptId") Long promptId,
            @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @LoginUser User user
    ) {
        return new ResponseDto<>(chatRoomSidebarService.updatePromptEmoji(promptId, updateEmojiRequestDto, user));
    }

}
