package konkuk.jokubattle.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import konkuk.jokubattle.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    private final Long ACCESS_TOKEN_EXPIRE_MILLIS = 12 * 60 * 60 * 1000L; // 12 hours

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .claim("category", "access")
                .claim("idx", user.getUsIdx())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_MILLIS))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean verify(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.debug("잘못된 Jwt 서명입니다.");
            throw new IllegalArgumentException("JWT 검증 중 오류가 발생했습니다.");
        } catch (ExpiredJwtException e) {
            log.debug("만료된 토큰입니다.");
            throw new IllegalArgumentException("JWT 검증 중 오류가 발생했습니다.");
        } catch (UnsupportedJwtException e) {
            log.debug("지원하지 않는 토큰입니다.");
            throw new IllegalArgumentException("JWT 검증 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            log.debug("잘못된 토큰입니다.");
            throw new IllegalArgumentException("JWT 검증 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new IllegalArgumentException("JWT 검증 중 오류가 발생했습니다.");
        }

        return true;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUsIdx(String token) {
        return Long.parseLong(getClaims(token).get("idx").toString());
    }

}
