package ai.platform.proma.util.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthSignUpDto {
    private String socialId;
    private String socialEmail;
    private String socialName;
    private String profileImage;

    @Builder
    public OauthSignUpDto(String socialId, String socialEmail, String socialName, String profileImage) {
        this.socialId = socialId;
        this.socialEmail = socialEmail;
        this.socialName = socialName;
        this.profileImage = profileImage;
    }

    public static OauthSignUpDto of(String socialId, String socialEmail, String socialName, String profileImage) {
        return OauthSignUpDto.builder()
                .socialId(socialId)
                .socialEmail(socialEmail)
                .socialName(socialName)
                .profileImage(profileImage)
                .build();
    }
}
