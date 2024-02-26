package api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class TokenServiceTest {

    private TokenService tokenService;

    private final Long EXPIRES_IN = 3_600_000L;
    private final String SECRET_TOKEN = "sC9IDSslynrf7pUDsKhf1BdzkVjkGyuCwXyfliHmBBIbOWS6Y4HWbiB3FoAivQRik74jWxWFQI8LVcw4Le78vQ";

    private final String emailPayload = "payload@payload.com";

    @BeforeEach
    void setUp() {
        this.tokenService = new TokenService(EXPIRES_IN, SECRET_TOKEN);
    }

    @Test
    void generate_token_should_return_non_null_token() {
        String result = this.tokenService.generateToken(emailPayload);
        assertNotNull(result);
    }

    @Test
    void generate_token_should_set_email_in_token_subject() {
        String token = this.tokenService.generateToken(emailPayload);
        String result = JWT.decode(token).getSubject();
        assertEquals(emailPayload, result);
    }

    @Test
    void generate_token_should_set_non_null_issued_at() {
        String token = this.tokenService.generateToken(emailPayload);
        Date result = JWT.decode(token).getIssuedAt();
        assertNotNull(result);
    }

    @Test
    void generate_token_should_set_non_null_expires_at() {
        String token = this.tokenService.generateToken(emailPayload);
        Date result = JWT.decode(token).getExpiresAt();
        assertNotNull(result);
    }

    @Test
    void generate_token_should_set_valid_expires_at() {
        String token = this.tokenService.generateToken(emailPayload);
        DecodedJWT decoded = JWT.decode(token);
        Date issuedAt = decoded.getIssuedAt();
        Date expiresAt = decoded.getExpiresAt();
        assertEquals(new Date(issuedAt.getTime() + EXPIRES_IN), expiresAt);
    }

    @Test
    void is_token_valid_should_return_true_when_token_is_valid() {
        String token = this.tokenService.generateToken(emailPayload);
        boolean result = this.tokenService.isTokenValid(token);
        assertTrue(result);
    }

    @Test
    void is_token_valid_should_return_False_when_token_is_invalid() {
        Boolean result = this.tokenService.isTokenValid(emailPayload);
        assertFalse(result);
    }

    @Test
    void get_token_subject_should_return_user_email() {
        String token = this.tokenService.generateToken(emailPayload);
        String result = this.tokenService.getTokenSubject(token);
        assertEquals(emailPayload, result);
    }
}