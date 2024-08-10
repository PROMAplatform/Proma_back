package ai.platform.proma.controller;


import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.BlockCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.security.LoginUser;
import ai.platform.proma.service.PromptMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prompt")
public class PromptMakerController {

    private final PromptMakerService promptMakerService;

    @PostMapping("/block/save")
    public ResponseDto<Boolean> makeBlock(
            @Valid @RequestBody BlockSaveRequestDto blockSaveRequestDto,
            @LoginUser User user
    ) {
        return new ResponseDto<>(promptMakerService.makeBlock(blockSaveRequestDto, user));
    }

    @GetMapping("/block")
    public ResponseDto<Map<String, List<SelectBlockDto>>> searchBlock(
            @LoginUser User user,
            @Valid @RequestParam("promptMethod") String promptMethod
    ) {
        return new ResponseDto<>(promptMakerService.searchBlock(user, promptMethod));
    }

    @DeleteMapping("/block/delete/{blockId}")
    public ResponseDto<Boolean> deleteBlock(
            @PathVariable Long blockId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(promptMakerService.deleteBlock(blockId, user));
    }

    @PostMapping("/save")
    public ResponseDto<Boolean> makePrompt(
        @Valid @RequestBody PromptSaveRequestDto promptSaveRequestDto,
        @LoginUser User user
    ) {
        return new ResponseDto<>(promptMakerService.makePrompt(promptSaveRequestDto, user));
    }
}
