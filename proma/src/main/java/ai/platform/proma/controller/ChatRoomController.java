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

    @GetMapping("/room/{chatRoomId}")
    public ResponseDto<Map<String, List<MessageListResponseDto>>> enterChatRoom(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomService.enterChatRoom(chatRoomId, userId));
    }


}
