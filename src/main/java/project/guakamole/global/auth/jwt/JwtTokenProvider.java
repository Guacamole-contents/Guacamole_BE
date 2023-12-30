package project.guakamole.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.guakamole.global.auth.security.CustomerUser;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Getter
public class JwtTokenProvider {

    @Value("${app.auth.accessTokenExpiry}")
    private String accessTokenExpiry;

    @Value("${app.auth.refreshTokenExpiry}")
    private String refreshTokenExpiry;

    @Value("${app.auth.tokenSecret}")
    private String secretKey;
    private SecretKey key;
    private static final String AUTHORITIES_KEY = "role";

    public JwtTokenProvider() {
    }

    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public SecretKey getSecretKey() {
        if (key == null) key = _getSecretKey();

        return key;
    }

    public JwtToken createJwtToken(Long userId, String userRole) {
        Date accessExpiryDate = getExpiryDate(accessTokenExpiry);
        Date refreshExpiryDate = getExpiryDate(refreshTokenExpiry);
        return JwtToken.builder()
                .userId(userId)
                .role(userRole)
                .accessExpiry(accessExpiryDate)
                .refreshExpiry(refreshExpiryDate)
                .key(getSecretKey())
                .build();
    }

    public JwtToken convertJwtToken(String accessToken, String refreshToken) {
        return JwtToken.of(accessToken, refreshToken, getSecretKey());
    }

    public static Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + (Long.parseLong(expiry) * 1000L));
    }

    public Authentication getAuthentication(JwtToken jwtToken) {

        Claims claims = jwtToken.getTokenClaims();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        int userId = (int) claims.get("userId");

        CustomerUser customUser = CustomerUser.builder()
                .userId((long) userId)
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(customUser, jwtToken, authorities);
    }

    public CustomerUser getCustomUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomerUser) authentication.getPrincipal();
    }
}
