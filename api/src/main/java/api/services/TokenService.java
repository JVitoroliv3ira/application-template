package api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TokenService {
    private final Long EXPIRES_IN;
    private final Algorithm ALGORITHM;

    public TokenService(
            @Value("${jwt.expires_in}") Long EXPIRES_IN,
            @Value("${jwt.secret_token}") String SECRET_TOKEN
    ) {
        this.EXPIRES_IN = EXPIRES_IN;
        this.ALGORITHM = Algorithm.HMAC256(SECRET_TOKEN);
    }

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.EXPIRES_IN))
                .sign(this.ALGORITHM);
    }

    public Boolean isTokenValid(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.ALGORITHM).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public String getTokenSubject(String token) {
        return JWT.decode(token).getSubject();
    }
}
