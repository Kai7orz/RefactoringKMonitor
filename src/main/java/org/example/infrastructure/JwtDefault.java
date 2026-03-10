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
import org.example.core.RoleRepository;
import org.example.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtDefault implements JwtService {

    private final SecretKey signinKey;
    private final SignatureAlgorithm signatureAlgorithm;
    private final int sessionTime;

    public JwtDefault(@Value("${jwt.secret}") String secret, @Value("${jwt.sessionTime}") int sessionTime) {
        this.signatureAlgorithm = SignatureAlgorithm.HS256;
        this.sessionTime = sessionTime;
        this.signinKey = new SecretKeySpec(secret.getBytes(), signatureAlgorithm.getJcaName());
    }
    // User mail address に紐づいたアクセストークンを発行し返却
    // sub と roles を含める
    // リフレッシュトークンは DB に収める
    // アクセストークンはレスポンスボディに入れて返すが，リフレッシュトークンは HTTPOnly で返す
    // toToken は service 側で user に紐づいたrole も取得して，それを toToken で JWT に含める
    public String toToken(User user) {
        Map<String, Integer> map = new HashMap<>();
        map.put("roleId",user.getRoleId());
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

    public Integer extractUserRoleId(String token) {
        Claims claim = Jwts.parserBuilder().setSigningKey(this.signinKey).build().parseClaimsJws(token).getBody();
        return Integer.valueOf(claim.get("roleId").toString());
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000L);
    }
}
