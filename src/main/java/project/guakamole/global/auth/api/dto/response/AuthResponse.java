package project.guakamole.global.auth.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.guakamole.global.auth.jwt.JwtToken;

@Getter
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;

    @Builder
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthResponse of(JwtToken jwtToken){
        return AuthResponse
                .builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }

}
