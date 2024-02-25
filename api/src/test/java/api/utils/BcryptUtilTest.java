package api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class BcryptUtilTest {
    private BcryptUtil bcryptUtil;
    @Mock
    private final PasswordEncoder encoder;

    @Autowired
    public BcryptUtilTest(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @BeforeEach
    void setUp() {
        this.bcryptUtil = new BcryptUtil(this.encoder);
    }

    @Test
    void encode_should_call_encode_method_of_password_encoder() {
        String payload = "@payload";
        this.bcryptUtil.encode(payload);
        verify(this.encoder, times(1)).encode(payload);
    }

    @Test
    void encode_should_return_result_of_encode_method_of_password_encoder() {
        String payload = "@payload";
        String expected = "payload@";
        when(this.encoder.encode(payload)).thenReturn(expected);
        String result = this.bcryptUtil.encode(payload);
        assertEquals(expected, result);
    }

    @Test
    void matches_should_call_matches_method_of_password_encoder() {
        String rawPayload = "@payload";
        String encodedPayload = "payload@";
        this.bcryptUtil.matches(rawPayload, encodedPayload);
        verify(this.encoder, times(1)).matches(rawPayload, encodedPayload);
    }

    @Test
    void matches_should_return_true_when_values_matches() {
        String rawPayload = "@payload";
        String encodedPayload = "payload@";
        when(this.encoder.matches(rawPayload, encodedPayload)).thenReturn(true);
        Boolean result = this.bcryptUtil.matches(rawPayload, encodedPayload);
        assertTrue(result);
    }

    @Test
    void matches_should_return_false_when_values_does_not_matches() {
        String rawPayload = "@payload";
        String encodedPayload = "payload@";
        when(this.encoder.matches(rawPayload, encodedPayload)).thenReturn(false);
        Boolean result = this.bcryptUtil.matches(rawPayload, encodedPayload);
        assertFalse(result);
    }
}