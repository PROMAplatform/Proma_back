package ai.platform.proma.controller;


import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.service.PromptMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prompt")
public class PromptMakerController {

    private final PromptMakerService promptMakerService;

    @PostMapping("/block/save")
    public ResponseDto<Boolean> makeBlock(
            @Valid @RequestBody BlockSaveRequestDto blockSaveRequestDto
            ) {
        return new ResponseDto<>(promptMakerService.makeBlock(blockSaveRequestDto));
    }

//    @GetMapping("/prompt/block")
//    public ResponseDto<List<SelectBlockDto>> searchBlock(
//         @Valid @RequestParam("userId") Long userId,
//         @Valid @RequestParam("promptMethod") PromptMethod promptMethod
//    ) {
//        return new ResponseDto<>(promptMakerService.searchBlock(userId, promptMethod));
//    }

    @PostMapping("/prompt/save")
    public ResponseDto<Boolean> makePrompt(
        @Valid @RequestBody PromptSaveRequestDto promptSaveRequestDto,
        @Valid @RequestParam("userId") Long userId
    ) {
        return new ResponseDto<>(promptMakerService.makePrompt(promptSaveRequestDto, userId));
    }
}
