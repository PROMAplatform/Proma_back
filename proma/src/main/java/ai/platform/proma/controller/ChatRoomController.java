package ai.platform.proma.controller;

import ai.platform.proma.dto.request.ListPromptAtom;
import ai.platform.proma.dto.response.MessageListResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/{chatRoomId}")
    public ResponseDto<Map<String, List<MessageListResponseDto>>> enterChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomService.enterChatRoom(chatRoomId, userId));
    }

    @PatchMapping("/prompt/block/{promptId}")
    public ResponseDto<Boolean> blockPrompt(
            @PathVariable("promptId") Long promptId,
            @RequestParam("userId") Long userId,
            @RequestBody List<ListPromptAtom> listPromptAtoms
            ) {
        return new ResponseDto<>(chatRoomService.updatePromptBlock(listPromptAtoms, promptId, userId));
    }
}
