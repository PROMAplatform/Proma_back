package ai.platform.proma.service;

import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.Role;
import ai.platform.proma.domain.enums.UserLoginMethod;
import ai.platform.proma.dto.response.LoginResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.security.JwtProvider;
import ai.platform.proma.security.JwtToken;
import ai.platform.proma.util.OauthUtil;
import ai.platform.proma.util.dto.OauthSignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OauthUtil oauthUtil;
    private final JwtProvider jwtProvider;

    public LoginResponseDto socialSignIn(String authCode, UserLoginMethod userLoginMethod) {
        String socialId = null;
        String socialName = null;
        String email = null;
        switch (userLoginMethod) {
            case KAKAO:
                String kakaoAccessToken = oauthUtil.getKakaoAccessToken(authCode);
                OauthSignUpDto kakaoInfo = oauthUtil.getKakaoUserInfo(kakaoAccessToken);
                email = kakaoInfo.getSocialEmail();
                socialId = kakaoInfo.getSocialId();
                socialName = kakaoInfo.getSocialName();
                break;
            case NAVER:
                String naverAccessToken = oauthUtil.getNaverAccessToken(authCode);
                OauthSignUpDto naverInfo = oauthUtil.getNaverUserInfo(naverAccessToken);
                email = naverInfo.getSocialEmail();
                socialId = naverInfo.getSocialId();
                socialName = naverInfo.getSocialName();
                break;
            case GOOGLE:
                String googleAccessToken = oauthUtil.getGoogleAccessToken(authCode);
                OauthSignUpDto googleInfo = oauthUtil.getGoogleUserInfo(googleAccessToken);
                email = googleInfo.getSocialEmail();
                socialId = googleInfo.getSocialId();
                socialName = googleInfo.getSocialName();
                break;
        }

        User user = userRepository.findBySocialIdAndUserLoginMethod(socialId, userLoginMethod);

        if(user == null){
            user = User.toEntity(email, socialName, userLoginMethod, socialId);
            userRepository.save(user);
        }
        JwtToken jwtToken = jwtProvider.createTotalToken(user.getSocialId(), user.getRole());

        user.signIn(jwtToken.getRefreshToken());

        return LoginResponseDto.of(jwtToken, user.getUserName());
    }

    public Boolean signOut(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        user.signOut();
        return true;
    }

    public Map<String, String> reissueToken(HttpServletRequest request, Role role) {
        return Map.of("access_token", jwtProvider.reissueToken(request, role));
    }

    public Boolean resignUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        user.resign();
        return true;
    }




//    public UserResponseDto findById(UserRequestDto userRequestDto) {
//        User user = userRepository.findById(userRequestDto.getUserId())
//                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
//
//        return UserResponseDto.builder()
//                .userName(user.getUserName())
//                .build();
//    }
//    public Boolean userSecession(UserRequestDto userRequestDto){
//
//        User user = userRepository.findById(userRequestDto.getUserId())
//                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
//
//        user.secession();
//        return true;
//    }


}
