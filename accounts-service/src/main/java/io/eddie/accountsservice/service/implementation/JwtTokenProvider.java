package io.eddie.accountsservice.service.implementation;

import io.eddie.accountsservice.model.dto.TokenBody;
import io.eddie.accountsservice.service.TokenProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtTokenProvider implements TokenProvider {

    @Value("${custom.jwt.secrets.app-key}")
    private String appKey;


    @Override
    public String issue(Long validTime, Map<String, Object> claims) {
        JwtBuilder builder = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + validTime))
                .signWith(getSecretKey(), Jwts.SIG.HS256);

        for (Map.Entry<String, Object> claimEntry : claims.entrySet()) {
            builder.claim(claimEntry.getKey(), claimEntry.getValue());
        }

        return builder.compact();
    }

    @Override
    public boolean validate(String t) {
        try {
            getClaims(t);
            return true;
        } catch ( JwtException e ) {
            log.info("Invalid JWT Token was detected: {}  msg : {}", t ,e.getMessage());
        } catch ( IllegalStateException e ) {
            log.info("JWT claims String is empty: {}  msg : {}", t ,e.getMessage());
        } catch ( Exception e ) {
            log.error("an error raised from validating token : {}  msg : {}", t ,e.getMessage());
        }

        return false;
    }

    @Override
    public TokenBody parse(String t) {
        return new TokenBody(
                getClaims(t).get("accountCode").toString()
        );
    }

    @Override
    public Map<String, Object> getClaims(String t) {
        Jws<Claims> parsed = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(t);

        return parsed.getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(appKey.getBytes());
    }
}
