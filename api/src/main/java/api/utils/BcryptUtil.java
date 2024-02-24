package api.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BcryptUtil {
    private final PasswordEncoder encoder;

    public String encode(String rawValue) {
        return this.encoder.encode(rawValue);
    }

    public Boolean matches(String rawValue, String encodedValue) {
        return this.encoder.matches(rawValue, encodedValue);
    }
}
