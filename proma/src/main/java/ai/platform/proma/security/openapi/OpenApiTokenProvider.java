package ai.platform.proma.security.openapi;

import ai.platform.proma.domain.enums.Role;
import ai.platform.proma.repositroy.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

@Component
public class OpenApiTokenProvider implements InitializingBean {

    private Long accessExpiredMs = 31536000000L;

    private String secretKey;
    private Key key;

    public OpenApiTokenProvider() {
        this.secretKey = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    @Override
    public void afterPropertiesSet() {
        this.secretKey = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    public Key getKey(String secretKey) {
        return key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(String id, Role role, Long promptId, boolean isAccess) {
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("id", id);
        if (isAccess) {
            claims.put("role", role);
            claims.put("promptId", promptId);
        }

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredMs))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public OpenApiToken createTotalToken(String id, Role role, Long promptId) {
        return OpenApiToken.of(
                createToken(id, role, promptId, true),
                secretKey
        );
    }

    public Claims validateToken(String token, String secretKey) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
