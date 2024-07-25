package ai.platform.proma.controller;

import ai.platform.proma.dto.request.UserRequestDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.UserResponseDto;
import ai.platform.proma.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/name")
    public ResponseDto<UserResponseDto> findUserName(
            @Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseDto<>(userService.findById(userRequestDto));
    }

    @GetMapping("/user/secession")
    public ResponseDto<Boolean> userSecession(
            @Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseDto<>(userService.userSecession(userRequestDto));
    }



}
