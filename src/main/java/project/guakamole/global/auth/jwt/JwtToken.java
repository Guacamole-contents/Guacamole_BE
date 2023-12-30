package project.guakamole.global.auth.jwt;

import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Getter
public class JwtToken {

    private static final String AUTHORITIES_KEY = "role";

    @Getter
    private final String accessToken;
    @Getter
    private final String refreshToken;
    private final Key key;

    public JwtToken(String accessToken, String refreshToken, Key key){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.key = key;
    }

    public static JwtToken of(String accessToken, String refreshToken, Key key){
        return new JwtToken(accessToken, refreshToken, key);
    }

    @Builder
    JwtToken(String role, Long userId, Date accessExpiry, Date refreshExpiry, Key key) {
        this.key = key;
        this.accessToken = createAccessToken(role, userId, accessExpiry);
        this.refreshToken = createRefreshToken(refreshExpiry);
    }

    private String createAccessToken(String role, Long userId, Date expiry) {
        return Jwts
                .builder()
                .claim(AUTHORITIES_KEY, role)
                .claim("userId", userId)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createRefreshToken(Date expiry) {
        Claims claims = Jwts
                .claims();

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidTokenClaims() {
        Optional<Object> claims = Optional.empty();
        try {
            claims = Optional.ofNullable(getTokenClaims());
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

    public Claims getTokenClaims() {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        Jws<Claims> claims = jwtParser.parseClaimsJws(accessToken);
        return claims.getBody();
    }

}