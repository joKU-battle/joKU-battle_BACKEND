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
import konkuk.jokubattle.global.exception.CustomException;
import konkuk.jokubattle.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    private final Long ACCESS_TOKEN_EXPIRE_MILLIS;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret}") String secretKey,
                       @Value("${jwt.accessTokenExpiration}") Long accessTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.ACCESS_TOKEN_EXPIRE_MILLIS = accessTokenExpiration;
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
            throw new CustomException(ErrorCode.JWT_ERROR_TOKEN);
        } catch (ExpiredJwtException e) {
            log.debug("만료된 토큰입니다.");
            throw new CustomException(ErrorCode.JWT_EXPIRE_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.debug("지원하지 않는 토큰입니다.");
            throw new CustomException(ErrorCode.JWT_ERROR_TOKEN);
        } catch (IllegalArgumentException e) {
            log.debug("잘못된 토큰입니다.");
            throw new CustomException(ErrorCode.JWT_ERROR_TOKEN);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new CustomException(ErrorCode.JWT_ERROR_TOKEN);
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
