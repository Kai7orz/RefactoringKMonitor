package org.example.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.security.Keys;
import org.example.api.exception.AuthenticationException;
import org.example.core.JwtService;
import org.example.core.user.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;

@Component
public class JwtDefault implements JwtService {
    private final SecretKey signinKey;
    private final int sessionTime;

    public JwtDefault(@Value("${jwt.sessionTime") int sessionTime) {
        this.signinKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.sessionTime = sessionTime;
    }
    // User mail address に紐づいたアクセストークンを発行し返却
    // sub と roles を含める
    // リフレッシュトークンは DB に収める
    // アクセストークンはレスポンスボディに入れて返すが，リフレッシュトークンは HTTPOnly で返す
    // toToken は service 側で user に紐づいたrole も取得して，それを toToken で JWT に含める
    public String toToken(User user,String roleName) {
        Map<String, String> map = new HashMap<>();
        map.put("role",roleName);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(map)
                .setExpiration(expireTimeFromNow())
                .signWith(signinKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(this.signinKey).parse(token);
            return true;
        } catch(Exception e){
            throw new AuthenticationException("無効なトークン");
        }
    }

    public String extractUserRole(String token) {
        Claims claim = Jwts.parserBuilder().setSigningKey(this.signinKey).build().parseClaimsJws(token).getBody();
        return claim.get("role").toString();
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000L);
    }
}
