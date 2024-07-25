package ai.platform.proma.controller;

import ai.platform.proma.dto.response.ChatRoomListResponseDto;
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

    @GetMapping("/room/list")
    public ResponseDto<Map<String, List<ChatRoomListResponseDto>>> chatRoomList(
            @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(chatRoomService.getChatRoomList(userId));
    }


}
