package project.guakamole.global.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";

    private final static String HEADER_REFRESH_TOKEN = "Refresh-token";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {

        String httpHeaderAuthorizationString = request.getHeader(HEADER_AUTHORIZATION);

        if (httpHeaderAuthorizationString == null ||
                !httpHeaderAuthorizationString.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        return httpHeaderAuthorizationString.substring(TOKEN_PREFIX.length());
    }

    public static String getRefreshToken(HttpServletRequest request) {

        return request.getHeader(HEADER_REFRESH_TOKEN);
    }

}