package com.example._14obnida.auth.security.config;

import com.example._14obnida.auth.security.AccessTokenInfo;
import com.example._14obnida.auth.security.JwtProperties;
import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtProvider {
    private static final String TOKEN_ISSUER = "Ddubuk";
    private static final String TOKEN_TYPE = "type";
    private  static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private static final String TOKEN_ROLE="role";

    private static final int MILLI_TO_SECOND=1000;

    private final JwtProperties jwtProperties;

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new DdubukException(ErrorCode.TOKEN_EXPIRED_ERROR);
        } catch (Exception e) {
            throw new DdubukException(ErrorCode.INVALID_TOKEN_ERROR);
        }
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)
        );
    }

    private String buildAccessToken(
            Long id, Date issuedAt, Date accessExpiresIn, String role
    ) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .claim(TOKEN_ROLE, role)
                .setExpiration(accessExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    public String generateAccessToken(Long id, String role) {
        final Date issuedAt = new Date();
        final Date accessExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getAccessExp()* MILLI_TO_SECOND);

        return buildAccessToken(id, issuedAt, accessExpiresIn, role);
    }

    public boolean isAccessToken(String token) {
        String typeClaim = getJws(token).getBody().get(TOKEN_TYPE).toString();
        log.info("Type claim value: {}", typeClaim);
        return typeClaim.equals(ACCESS_TOKEN);
    }

    public AccessTokenInfo parseAccessToken(String token) {
        if(isAccessToken(token)) {
            Claims claims = getJws(token).getBody();
            return  AccessTokenInfo.builder()
                    .userId(Long.parseLong(claims.getSubject()))
                    .role((String) claims.get(TOKEN_ROLE))
                    .build();
        }
        throw new DdubukException(ErrorCode.INVALID_TOKEN_ERROR);
    }

    public Long getAccessTokenTTlSecond() {
        return jwtProperties.getAccessExp();
    }

    public Long getLeftAccessTokenTTlSecond(String token){
        return getJws(token).getBody().getExpiration().getTime();
    }
}
