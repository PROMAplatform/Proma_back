package ai.platform.proma.controller;


import ai.platform.proma.dto.request.*;
import ai.platform.proma.dto.response.*;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.usecase.chatroom.ChatRoomUpdatePromptBlockUseCase;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarDeletePromptUseCase;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarGetPromptListUseCase;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdatePromptDetailUseCase;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdatePromptEmojiUseCase;
import ai.platform.proma.usecase.post.PostDistributePromptUseCase;
import ai.platform.proma.usecase.post.PostPromptDetailUseCase;
import ai.platform.proma.usecase.post.PostPromptTitleListUseCase;
import ai.platform.proma.usecase.prompt.PromptHistorySaveUsecase;
import ai.platform.proma.usecase.prompt.PromptMakePromptUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prompts")
public class PromptMakerController {

    private final PromptMakePromptUseCase promptMakePromptUseCase;
    private final SidebarGetPromptListUseCase sidebarGetPromptListUseCase;
    private final SidebarUpdatePromptDetailUseCase sidebarUpdatePromptDetailUseCase;
    private final SidebarUpdatePromptEmojiUseCase sidebarUpdatePromptEmojiUseCase;
    private final SidebarDeletePromptUseCase sidebarDeletePromptUseCase;
    private final ChatRoomUpdatePromptBlockUseCase chatRoomUpdatePromptBlockUseCase;
    private final PostPromptTitleListUseCase postPromptTitleListUseCase;
    private final PostPromptDetailUseCase postPromptDetailUseCase;
    private final PostDistributePromptUseCase postDistributePromptUseCase;
    private final PromptHistorySaveUsecase promptHistorySaveUsecase;

    @PostMapping("")
    public ResponseDto<Boolean> makePrompt(
        @Valid @RequestBody PromptSaveRequestDto promptSaveRequestDto,
        @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptMakePromptUseCase.makePrompt(promptSaveRequestDto, userId));
    }

    @PostMapping("/history")
    public ResponseDto<Boolean> makePromptHistory(
            @Valid @RequestBody PromptHistorySaveReuqestDto promptHistorySaveReuqestDto,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptHistorySaveUsecase.execute(userId, promptHistorySaveReuqestDto));
    }

    @PostMapping("/{promptId}")
    public ResponseDto<Boolean> distributePrompt(
            @PathVariable("promptId") Long promptId,
            @Valid @RequestBody PostDistributeRequestDto postDistributeRequestDto,
            @LoginUser Long userId    ) {
        return new ResponseDto<>(postDistributePromptUseCase.distributePrompt(userId, promptId, postDistributeRequestDto));
    }

    @GetMapping("")
    public ResponseDto<Map<String, List<PromptListResponseDto>>> promptList(
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarGetPromptListUseCase.getPromptList(userId));
    }

    @GetMapping("/{promptId}")
    public ResponseDto<PromptListResponseDto> promptDetail(
            @PathVariable("promptId") Long promptId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(postPromptDetailUseCase.promptDetail(promptId, userId));
    }

    @GetMapping("/titles")
    public ResponseDto<Map<String, List<PromptTitleList>>> promptTitleList(
            @LoginUser Long userId) {
        return new ResponseDto<>(postPromptTitleListUseCase.promptTitleList(userId));
    }

    @PatchMapping("/{promptId}")
    public ResponseDto<Boolean> updatePromptDetail(
            @Valid @RequestBody PromptDetailUpdateRequestDto promptDetailUpdateRequestDto,
            @PathVariable("promptId") Long promptId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarUpdatePromptDetailUseCase.updatePromptDetail(promptDetailUpdateRequestDto, promptId, userId));
    }

    @PatchMapping("/{promptId}/emojis")
    public ResponseDto<ChatRoomIdResponseDto> updatePromptEmoji(
            @PathVariable("promptId") Long promptId,
            @Valid @RequestBody UpdateEmojiRequestDto updateEmojiRequestDto,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarUpdatePromptEmojiUseCase.updatePromptEmoji(promptId, updateEmojiRequestDto, userId));
    }

    @PatchMapping("/{promptId}/blocks")
    public ResponseDto<Boolean> blockPrompt(
            @PathVariable("promptId") Long promptId,
            @LoginUser Long userId,
            @Valid @RequestBody PromptUpdateRequestDto promptUpdateRequestDto
    ) {
        return new ResponseDto<>(chatRoomUpdatePromptBlockUseCase.updatePromptBlock(promptUpdateRequestDto, promptId, userId));
    }

    @DeleteMapping("/{promptId}")
    public ResponseDto<Boolean> deletePrompt(
            @PathVariable("promptId") Long promptId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(sidebarDeletePromptUseCase.deletePrompt(promptId, userId));
    }

}
