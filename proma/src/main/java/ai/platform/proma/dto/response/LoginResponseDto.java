package ai.platform.proma.dto.response;

import ai.platform.proma.security.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String userName;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponseDto(String userName, String accessToken, String refreshToken) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginResponseDto of(JwtToken jwtToken, String userName) {
        return LoginResponseDto.builder()
                .userName(userName)
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }
}
