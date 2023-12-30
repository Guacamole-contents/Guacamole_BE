package project.guakamole.global.auth.api.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.global.auth.jwt.JwtToken;
import project.guakamole.global.auth.jwt.JwtTokenProvider;

import java.util.Optional;

@Transactional
@Service
@Slf4j
public class RefreshTokenService {
    private final JwtTokenProvider jwtTokenProvider;

    public RefreshTokenService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean isExpiredRefreshToken (String accessToken) {

        Optional<Object> claims = Optional.empty();
        try {
            claims= Optional.ofNullable(getTokenClaims(accessToken));
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        } catch (Exception e) {
            return false;
        }

        return claims.isPresent();
    }

    public Claims getTokenClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(jwtTokenProvider.getKey())
                .build();

        return jwtParser.parseClaimsJws(token).getBody();
    }
}