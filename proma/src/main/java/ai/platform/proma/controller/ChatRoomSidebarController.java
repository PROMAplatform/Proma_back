package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.service.ChatRoomSidebarService;
import ai.platform.proma.usecase.chatroom.sidebar.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting/sidebar")
public class ChatRoomSidebarController {

    private final ChatRoomSidebarService chatRoomSidebarService;
    private final SidebarSaveChatRoomUseCase sidebarSaveChatRoomUseCase;
    private final SidebarGetChatRoomListUseCase sidebarGetChatRoomListUseCase;
    private final SidebarUpdateChatRoomEmojiUseCase sidebarUpdateChatRoomEmojiUseCase;
    private final SidebarDeleteChatRoomUseCase sidebarDeleteChatRoomUseCase;
    private final SidebarUpdatePromptDetailUseCase sidebarUpdatePromptDetailUseCase;
    private final SidebarDeletePromptUseCase sidebarDeletePromptUseCase;
    private final SidebarGetPromptListUseCase sidebarGetPromptListUseCase;
    private final SidebarUpdatePromptEmojiUseCase sidebarUpdatePromptEmojiUseCase;


    @PostMapping("/room/save")
    public ResponseDto<ChatRoomIdResponseDto> saveChatRoom(
        @RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto,
        @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarSaveChatRoomUseCase.saveChatRoom(chatRoomSaveRequestDto, user));
    }

    @GetMapping("/room/list")
    public ResponseDto<Map<String, List<ChatRoomListResponseDto>>> chatRoomList(
            @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarGetChatRoomListUseCase.getChatRoomList(user));
    }

    @PatchMapping("/room/emoji/{chatRoomId}")
    public ResponseDto<ChatRoomIdResponseDto> updateChatRoomEmoji(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @LoginUser User user
            ) {
        return new ResponseDto<>(sidebarUpdateChatRoomEmojiUseCase.updateChatRoomEmoji(chatRoomId, updateEmojiRequestDto, user));
    }

    @DeleteMapping("/room/{chatRoomId}")
    public ResponseDto<Boolean> deleteChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarDeleteChatRoomUseCase.deleteChatRoom(chatRoomId, user));
    }

    @PatchMapping("/prompt/{promptId}")
    public ResponseDto<Boolean> updatePromptDetail(
            @RequestBody PromptDetailUpdateRequestDto promptDetailUpdateRequestDto,
            @PathVariable("promptId") Long promptId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarUpdatePromptDetailUseCase.updatePromptDetail(promptDetailUpdateRequestDto, promptId, user));
    }

    @DeleteMapping("/prompt/{promptId}")
    public ResponseDto<Boolean> deletePrompt(
            @PathVariable("promptId") Long promptId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarDeletePromptUseCase.deletePrompt(promptId, user));
    }

    @GetMapping("/prompt/list")
    public ResponseDto<Map<String, List<PromptListResponseDto>>> promptList(
            @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarGetPromptListUseCase.getPromptList(user));
    }

    @PatchMapping("/prompt/emoji/{promptId}")
    public ResponseDto<ChatRoomIdResponseDto> updatePromptEmoji(
            @PathVariable("promptId") Long promptId,
            @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @LoginUser User user
    ) {
        return new ResponseDto<>(sidebarUpdatePromptEmojiUseCase.updatePromptEmoji(promptId, updateEmojiRequestDto, user));
    }

}
