package ai.platform.proma.controller;


import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.service.PromptMakerService;
import ai.platform.proma.usecase.prompt.PromptDeleteBlockUseCase;
import ai.platform.proma.usecase.prompt.PromptMakeBlockUseCase;
import ai.platform.proma.usecase.prompt.PromptMakePromptUseCase;
import ai.platform.proma.usecase.prompt.PromptSearchBlockUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prompt")
public class PromptMakerController {

    private final PromptMakerService promptMakerService;
    private final PromptMakeBlockUseCase promptMakeBlockUseCase;
    private final PromptSearchBlockUseCase promptSearchBlockUseCase;
    private final PromptDeleteBlockUseCase promptDeleteBlockUseCase;
    private final PromptMakePromptUseCase promptMakePromptUseCase;
    @PostMapping("/block/save")
    public ResponseDto<Boolean> makeBlock(
            @Valid @RequestBody BlockSaveRequestDto blockSaveRequestDto,
            @LoginUser User user
    ) {
        return new ResponseDto<>(promptMakeBlockUseCase.makeBlock(blockSaveRequestDto, user));
    }

    @GetMapping("/block")
    public ResponseDto<Map<String, List<SelectBlockDto>>> searchBlock(
            @LoginUser User user,
            @Valid @RequestParam("promptMethod") String promptMethod
    ) {
        return new ResponseDto<>(promptSearchBlockUseCase.searchBlock(user, promptMethod));
    }

    @DeleteMapping("/block/delete/{blockId}")
    public ResponseDto<Boolean> deleteBlock(
            @PathVariable Long blockId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(promptDeleteBlockUseCase.deleteBlock(blockId, user));
    }

    @PostMapping("/save")
    public ResponseDto<Boolean> makePrompt(
        @Valid @RequestBody PromptSaveRequestDto promptSaveRequestDto,
        @LoginUser User user
    ) {
        return new ResponseDto<>(promptMakePromptUseCase.makePrompt(promptSaveRequestDto, user));
    }
}
