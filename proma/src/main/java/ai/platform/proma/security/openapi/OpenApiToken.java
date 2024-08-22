package ai.platform.proma.security.openapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OpenApiToken {
    private String accessToken;
    private String secretKey;


    public static OpenApiToken of(String accessToken, String secretKey) {
        System.err.println("aas");
        return OpenApiToken.builder()
                .accessToken(accessToken)
                .secretKey(secretKey)
                .build();
    }
}
