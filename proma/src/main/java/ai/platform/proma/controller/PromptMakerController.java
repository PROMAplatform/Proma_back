package ai.platform.proma.controller;


import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.annotation.LoginUser;
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

    private final PromptMakeBlockUseCase promptMakeBlockUseCase;
    private final PromptSearchBlockUseCase promptSearchBlockUseCase;
    private final PromptDeleteBlockUseCase promptDeleteBlockUseCase;
    private final PromptMakePromptUseCase promptMakePromptUseCase;
    @PostMapping("/block/save")
    public ResponseDto<Boolean> makeBlock(
            @Valid @RequestBody BlockSaveRequestDto blockSaveRequestDto,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptMakeBlockUseCase.makeBlock(blockSaveRequestDto, userId));
    }

    @GetMapping("/block")
    public ResponseDto<Map<String, List<SelectBlockDto>>> searchBlock(
            @LoginUser Long userId,
            @Valid @RequestParam("promptMethod") String promptMethod
    ) {
        return new ResponseDto<>(promptSearchBlockUseCase.searchBlock(userId, promptMethod));
    }

    @DeleteMapping("/block/delete/{blockId}")
    public ResponseDto<Boolean> deleteBlock(
            @PathVariable Long blockId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptDeleteBlockUseCase.deleteBlock(blockId, userId));
    }

    @PostMapping("/save")
    public ResponseDto<Boolean> makePrompt(
        @Valid @RequestBody PromptSaveRequestDto promptSaveRequestDto,
        @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptMakePromptUseCase.makePrompt(promptSaveRequestDto, userId));
    }
}
