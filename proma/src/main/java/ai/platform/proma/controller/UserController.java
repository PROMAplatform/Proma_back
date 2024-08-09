package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.Role;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.dto.request.UserRequestDto;
import ai.platform.proma.dto.response.LoginResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.dto.response.UserResponseDto;
import ai.platform.proma.security.LoginUser;
import ai.platform.proma.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth/user/")
public class UserController {

    private final UserService userService;

    @PostMapping("/social/kakao")
    public ResponseDto<LoginResponseDto> byKakao(
            @RequestParam("code") String code) {
        return new ResponseDto<>(userService.socialSignIn(code, UserLoginMethod.KAKAO));
    }

    @PostMapping("/social/naver")
    public ResponseDto<LoginResponseDto> byNaver(
            @RequestParam("code") String code) {
        return new ResponseDto<>(userService.socialSignIn(code, UserLoginMethod.NAVER));
    }

    @PostMapping("/social/google")
    public ResponseDto<LoginResponseDto> byGoogle(
            @RequestParam("code") String code) {
        return new ResponseDto<>(userService.socialSignIn(code, UserLoginMethod.GOOGLE));
    }

    @PatchMapping("/signout")
    public ResponseDto<Boolean> signOut(
            @LoginUser User user
    ){
        return new ResponseDto<>(userService.signOut(user));
    }

    @PostMapping("/social/reissue")
    public ResponseDto<Map<String, String>> reissueUser(HttpServletRequest request) {
        return new ResponseDto<>(userService.reissueToken(request, Role.USER));
    }

    @PatchMapping("/resign")
    public ResponseDto<Boolean> resignUser(
            @LoginUser User user
    ){
        return new ResponseDto<>(userService.resignUser(user));
    }

}
