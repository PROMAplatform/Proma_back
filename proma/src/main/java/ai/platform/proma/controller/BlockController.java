package ai.platform.proma.controller;

import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.usecase.prompt.PromptDeleteBlockUseCase;
import ai.platform.proma.usecase.prompt.PromptMakeBlockUseCase;
import ai.platform.proma.usecase.prompt.PromptSearchBlockUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocks")
public class BlockController {

    private final PromptMakeBlockUseCase promptMakeBlockUseCase;
    private final PromptSearchBlockUseCase promptSearchBlockUseCase;
    private final PromptDeleteBlockUseCase promptDeleteBlockUseCase;

    @PostMapping("")
    public ResponseDto<Boolean> makeBlock(
            @Valid @RequestBody BlockSaveRequestDto blockSaveRequestDto,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptMakeBlockUseCase.makeBlock(blockSaveRequestDto, userId));
    }

    @GetMapping("")
    public ResponseDto<Map<String, List<SelectBlockDto>>> searchBlock(
            @LoginUser Long userId,
            @Valid @RequestParam("promptMethod") String promptMethod
    ) {
        return new ResponseDto<>(promptSearchBlockUseCase.searchBlock(userId, promptMethod));
    }

    @DeleteMapping("/{blockId}")
    public ResponseDto<Boolean> deleteBlock(
            @PathVariable Long blockId,
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(promptDeleteBlockUseCase.deleteBlock(blockId, userId));
    }
}
